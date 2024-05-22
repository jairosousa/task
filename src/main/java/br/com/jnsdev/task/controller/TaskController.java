package br.com.jnsdev.task.controller;

import br.com.jnsdev.task.model.Task;
import br.com.jnsdev.task.service.TaskService;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.List;

/**
 * @Autor Jairo Nascimento
 * @Created 22/05/2024 - 17:00
 */
@RestController
@RequestMapping("/task")
public class TaskController {
    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping
    public Mono<List<Task>> getTask () {
        return taskService.list();
    }

    @PostMapping
    public Mono<Task> createTask(@RequestBody Task task) {
        return taskService.insert(task);
    }
}
