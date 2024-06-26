package br.com.jnsdev.task.controller;

import br.com.jnsdev.task.controller.converter.TaskDTOConverter;
import br.com.jnsdev.task.controller.converter.TaskInsertDTOConverter;
import br.com.jnsdev.task.controller.converter.TaskUpdateDTOConverter;
import br.com.jnsdev.task.controller.dto.TaskDTO;
import br.com.jnsdev.task.controller.dto.TaskInsertDTO;
import br.com.jnsdev.task.controller.dto.TaskUpdateDTO;
import br.com.jnsdev.task.model.TaskState;
import br.com.jnsdev.task.service.TaskService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

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
    private final TaskInsertDTOConverter insertDTOConverter;

    private final TaskUpdateDTOConverter updateDTOConverter;

    public TaskController(TaskService taskService, TaskDTOConverter converter, TaskInsertDTOConverter insertDTOConverter, TaskUpdateDTOConverter updateDTOConverter) {
        this.taskService = taskService;
        this.converter = converter;
        this.insertDTOConverter = insertDTOConverter;
        this.updateDTOConverter = updateDTOConverter;
    }

    @GetMapping
    public Mono<Page<TaskDTO>> getTask(@RequestParam(required = false) String id,
                                       @RequestParam(required = false) String title,
                                       @RequestParam(required = false) String description,
                                       @RequestParam(required = false, defaultValue = "0") int priority,
                                       @RequestParam(required = false) TaskState state,
                                       @RequestParam(value = "pageNumber", required = false, defaultValue = "0") Integer pageNumber,
                                       @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize
    ) {
        return taskService.findPaginated(converter.convert(id, title, description, priority, state), pageNumber, pageSize)
                .map(it -> it.map(converter::convert));
    }

    @PostMapping
    public Mono<TaskDTO> createTask(@RequestBody @Valid TaskInsertDTO taskInsertDTO) {
        return taskService.insert(insertDTOConverter.convert(taskInsertDTO))
                .doOnNext(task -> LOGGER.info("Saved task with id {}", task.getId()))
                .map(converter::convert);
    }

    @PutMapping
    public Mono<TaskDTO> updateTask(@RequestBody @Valid TaskUpdateDTO taskUpdateDTO) {
        return taskService.update(updateDTOConverter.convert(taskUpdateDTO))
                .doOnNext(it -> LOGGER.info("Update task with id {}", it.getId()))
                .map(converter::convert);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> delete(@PathVariable String id) {
        return Mono.just(id)
                .doOnNext(it -> LOGGER.info("Deleting task with id {}", it))
                .flatMap(taskService::deleteById);
    }

    @PostMapping(value = "/start")
    public Mono<TaskDTO> start(@RequestParam String id, @RequestParam String cep) {
        return taskService.start(id, cep)
                .map(converter::convert);
    }

    @PostMapping(value = "/refresh/created")
    public Flux<TaskDTO> refreshCreated() {
        return taskService.refreshCreated()
                .map(converter::convert);
    }

    @PostMapping(value = "/done")
    public Mono<List<TaskDTO>> done(@RequestBody List<String> ids) {
        return taskService.doneMany(ids)
                .map(converter::convertList);
    }
}
