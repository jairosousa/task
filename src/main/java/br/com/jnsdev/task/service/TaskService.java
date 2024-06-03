package br.com.jnsdev.task.service;

import br.com.jnsdev.task.exception.TaskNotFoundException;
import br.com.jnsdev.task.model.Task;
import br.com.jnsdev.task.repository.TaskCustomRepository;
import br.com.jnsdev.task.repository.TaskRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

/**
 * @Autor Jairo Nascimento
 * @Created 22/05/2024 - 16:12
 */
@Service
public class TaskService {
    private static final Logger LOGGER = LoggerFactory.getLogger(TaskService.class);

    private final TaskRepository taskRepository;
    private final TaskCustomRepository customRepository;

    public TaskService(TaskRepository taskRepository, TaskCustomRepository customRepository) {
        this.taskRepository = taskRepository;
        this.customRepository = customRepository;
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

    private Mono<Task> save(Task task) {
        return Mono.just(task)
                .doOnNext(t -> LOGGER.info("Saved task with title {}", t.getTitle()))
                .flatMap(taskRepository::save);
    }

}
