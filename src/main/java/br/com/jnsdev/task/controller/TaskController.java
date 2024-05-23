package br.com.jnsdev.task.controller;

import br.com.jnsdev.task.controller.converter.TaskDTOConverter;
import br.com.jnsdev.task.controller.dto.TaskDTO;
import br.com.jnsdev.task.model.Task;
import br.com.jnsdev.task.model.TaskState;
import br.com.jnsdev.task.service.TaskService;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

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
    public Page<TaskDTO> getTask(@RequestParam(required = false) String id,
                                 @RequestParam(required = false) String title,
                                 @RequestParam(required = false) String description,
                                 @RequestParam(required = false, defaultValue = "0") int priority,
                                 @RequestParam(required = false) TaskState state,
                                 @RequestParam(value = "pageNumber", required = false, defaultValue = "0") Integer pageNumber,
                                 @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize
    ) {
        return taskService.findPaginated(converter.converter(id, title, description, priority, state), pageNumber, pageSize)
                .map(converter::convert);
    }

    @PostMapping
    public Mono<TaskDTO> createTask(@RequestBody Task task) {
        return taskService.insert(task)
                .map(converter::convert);
    }
}
