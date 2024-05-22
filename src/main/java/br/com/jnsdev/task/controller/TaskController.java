package br.com.jnsdev.task.controller;

import br.com.jnsdev.task.controller.converter.TaskDTOConverter;
import br.com.jnsdev.task.controller.dto.TaskDTO;
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
    private final TaskDTOConverter converter;

    public TaskController(TaskService taskService, TaskDTOConverter converter) {
        this.taskService = taskService;
        this.converter = converter;
    }

    @GetMapping
    public Mono<List<TaskDTO>> getTask () {
        return taskService.list()
                .map(converter::convertList);
    }

    @PostMapping
    public Mono<TaskDTO> createTask(@RequestBody Task task) {
        return taskService.insert(task)
                .map(converter::convert);
    }
}
