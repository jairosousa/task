package br.com.jnsdev.task.controller;

import br.com.jnsdev.task.controller.converter.TaskDTOConverter;
import br.com.jnsdev.task.controller.dto.TaskDTO;
import br.com.jnsdev.task.model.TaskState;
import br.com.jnsdev.task.service.TaskService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

/**
 * @Autor Jairo Nascimento
 * @Created 22/05/2024 - 17:00
 */
@RestController
@RequestMapping("/task")
public class TaskController {
    private static final Logger LOGGER = LoggerFactory.getLogger(TaskController.class);
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
        return taskService.findPaginated(converter.convert(id, title, description, priority, state), pageNumber, pageSize)
                .map(converter::convert);
    }

    @PostMapping
    public Mono<TaskDTO> createTask(@RequestBody TaskDTO taskDTO) {
        return taskService.insert(converter.convert(taskDTO))
                .doOnNext(task -> LOGGER.info("Saved task with id {}", task.getId()))
                .map(converter::convert);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> delete(@PathVariable String id) {
        return Mono.just(id)
                .doOnNext(it -> LOGGER.info("Deleting task with id {}", it))
                .flatMap(taskService::deleteById);
    }
}
