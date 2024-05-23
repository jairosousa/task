package br.com.jnsdev.task.service;

import br.com.jnsdev.task.model.Task;
import br.com.jnsdev.task.model.TaskState;
import br.com.jnsdev.task.repository.TaskCustomRepository;
import br.com.jnsdev.task.repository.TaskRepository;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

/**
 * @Autor Jairo Nascimento
 * @Created 22/05/2024 - 16:12
 */
@Service
public class TaskService {

    private final TaskRepository taskRepository;
    private final TaskCustomRepository customRepository;

    public TaskService(TaskRepository taskRepository, TaskCustomRepository customRepository) {
        this.taskRepository = taskRepository;
        this.customRepository = customRepository;
    }

    public Mono<Task> insert(Task task) {
        return Mono.just(task)
                .map(Task::insert)
                .flatMap(this::save);
    }

    public Mono<List<Task>> list() {
        return Mono.just(taskRepository.findAll());
    }

    private Mono<? extends Task> save(Task task) {
        return Mono.just(task)
                .map(taskRepository::save);
    }

    public Page<Task> findPaginated(Task task, Integer pageNumber, Integer pageSize) {
        return customRepository.findPaginated(task, pageNumber, pageSize);
    }

    public Mono<Void> deleteById(String id) {
        return Mono.fromRunnable(()-> taskRepository.deleteById(id));
    }
}
