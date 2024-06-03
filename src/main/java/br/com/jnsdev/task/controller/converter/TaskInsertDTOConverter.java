package br.com.jnsdev.task.controller.converter;

import br.com.jnsdev.task.controller.dto.TaskDTO;
import br.com.jnsdev.task.controller.dto.TaskInsertDTO;
import br.com.jnsdev.task.model.Task;
import br.com.jnsdev.task.model.TaskState;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @Autor Jairo Nascimento
 * @Created 22/05/2024 - 17:31
 */
@Component
public class TaskInsertDTOConverter {
    public Task convert(TaskInsertDTO dto) {
        return Optional.ofNullable(dto)
                .map(source ->
                        Task.builder()
                                .title(source.getTitle())
                                .description(source.getDescription())
                                .priority(source.getPriority())
                                .build()).orElse(null);
    }

}
