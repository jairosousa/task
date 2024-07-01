package br.com.jnsdev.task.service;

import br.com.jnsdev.task.exception.TaskNotFoundException;
import br.com.jnsdev.task.messaging.TaskNotificationProducer;
import br.com.jnsdev.task.model.Address;
import br.com.jnsdev.task.model.Task;
import br.com.jnsdev.task.repository.TaskCustomRepository;
import br.com.jnsdev.task.repository.TaskRepository;
import jakarta.annotation.PostConstruct;
import org.reactivestreams.Publisher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * @Autor Jairo Nascimento
 * @Created 22/05/2024 - 16:12
 */
@Service
public class TaskService {
    private static final Logger LOGGER = LoggerFactory.getLogger(TaskService.class);

    private final TaskRepository taskRepository;
    private final TaskCustomRepository customRepository;
    private final AddressService addressService;
    private final TaskNotificationProducer notificationProducer;

    public TaskService(TaskRepository taskRepository, TaskCustomRepository customRepository, AddressService addressService, TaskNotificationProducer notificationProducer) {
        this.taskRepository = taskRepository;
        this.customRepository = customRepository;
        this.addressService = addressService;
        this.notificationProducer = notificationProducer;
    }

    public Mono<Task> insert(Task task) {
        return Mono.just(task)
                .map(Task::insert)
//                .flatMap(it -> doError()) // Para simular error
                .flatMap(this::save)
                .doOnError(error -> LOGGER.error("Error during save task. Title {}", task.getTitle(), error))
                .cast(Task.class);
    }

    /**
     * Para simular error
     *
     * @return
     */
//    public Mono<Task> doError() {
//        return Mono.error(new RuntimeException("Erro during teste"));
//    }
    public Mono<Page<Task>> findPaginated(Task task, Integer pageNumber, Integer pageSize) {
        return customRepository.findPaginated(task, pageNumber, pageSize);
    }

    public Mono<Task> update(Task task) {
        return taskRepository.findById(task.getId())
                .map(task::update)
                .flatMap(taskRepository::save)
                .switchIfEmpty(Mono.error(TaskNotFoundException::new))
                .doOnError(error -> LOGGER.error("Error during update task with id: {}. Message {}", task.getId(), error.getMessage()));
    }

    public Mono<Void> deleteById(String id) {
        return taskRepository.deleteById(id);
    }

    public Mono<Task> start(String id, String cep) {
        return taskRepository.findById(id)
                .zipWhen(it -> addressService.getAddress(cep))
                .doOnNext(it -> LOGGER.info("Retornando dados da API do ViaCepClient address: {}", it.getT2()))
                .flatMap(it -> updateAddress(it.getT1(), it.getT2()))
                .map(Task::start)
                .flatMap(taskRepository::save)
                .flatMap(notificationProducer::sendNotification)
                .switchIfEmpty(Mono.error(TaskNotFoundException::new))
                .doOnError(error -> LOGGER.error("Error on start task. ID {}", id, error));
    }

    public Mono<Task> done(Task task) {
        return Mono.just(task)
                .doOnNext(it -> LOGGER.info("Finishing task. ID: {}", task.getId()))
                .map(Task::done)
                .flatMap(taskRepository::save);
    }

    public Flux<Task> refreshCreated() {
        return taskRepository.findAll()
                .filter(Task::createdIsEmpty)
                .map(Task::createdNow)
                .flatMap(taskRepository::save);
    }

    public Mono<List<Task>> doneMany(List<String> ids) {
        return Flux.fromIterable(ids)
                .flatMap(id -> taskRepository.findById(id)
                        .map(Task::done)
                        .flatMap(taskRepository::save)
                        .doOnNext(it -> LOGGER.info("Done task. ID: {}", it.getId()))
                ).collectList();
    }

    private Mono<Task> updateAddress(Task task, Address address) {
        return Mono.just(task)
                .map(it -> it.updateAddress(address));
    }

    @PostConstruct
    private void scheduleDoneOlderTask() {
        Mono.delay(Duration.ofSeconds(5))
                .doOnNext(it -> LOGGER.info("Starting task monitoring"))
                .subscribe();

        Flux.interval(Duration.ofSeconds(10))
                .flatMap(it -> doneOlderTask())
                .filter(tasks -> tasks > 0)
                .doOnNext(tasks -> LOGGER.info("{} task(s) completed ofter being active for over 7 days", tasks))
                .subscribe();

    }

    private Mono<Long> doneOlderTask() {
        return customRepository.updateStateToDoneForOlderTask(LocalDate.now().minusDays(7));
    }

    private Mono<Task> save(Task task) {
        return Mono.just(task)
                .doOnNext(t -> LOGGER.info("Saved task with title {}", t.getTitle()))
                .flatMap(taskRepository::save);
    }

}
