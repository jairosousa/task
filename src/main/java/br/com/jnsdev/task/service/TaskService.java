package br.com.jnsdev.task.service;

import br.com.jnsdev.task.model.Task;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;

/**
 * @Autor Jairo Nascimento
 * @Created 22/05/2024 - 16:12
 */
@Service
public class TaskService {
    public static List<Task> taskList;

    public Mono<Task> insert(Task task){
        return Mono.just(task)
                .map(Task::insert)
                .flatMap(this::save);
    }

    public Mono<List<Task>> list() {
        return Mono.just(taskList);
    }

    private Mono<? extends Task> save(Task task) {
        return Mono.just(task)
                .map(Task::newTask);
    }
}
