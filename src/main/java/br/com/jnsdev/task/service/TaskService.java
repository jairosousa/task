package br.com.jnsdev.task.service;

import br.com.jnsdev.task.model.Task;
import br.com.jnsdev.task.repository.TaskRepository;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import org.springframework.stereotype.Service;
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

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
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
}
