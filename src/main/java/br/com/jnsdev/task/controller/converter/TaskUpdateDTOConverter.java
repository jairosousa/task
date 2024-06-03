package br.com.jnsdev.task.controller.converter;

import br.com.jnsdev.task.controller.dto.TaskUpdateDTO;
import br.com.jnsdev.task.model.Task;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * @Autor Jairo Nascimento
 * @Created 22/05/2024 - 17:31
 */
@Component
public class TaskUpdateDTOConverter {
    public Task convert(TaskUpdateDTO dto) {
        return Optional.ofNullable(dto)
                .map(source ->
                        Task.builder()
                                .id(source.getId())
                                .title(source.getTitle())
                                .description(source.getDescription())
                                .priority(source.getPriority())
                                .build()).orElse(null);
    }

}
