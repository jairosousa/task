package br.com.jnsdev.task.controller.converter;

import br.com.jnsdev.task.controller.dto.TaskDTO;
import br.com.jnsdev.task.model.Task;
import br.com.jnsdev.task.model.TaskState;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @Autor Jairo Nascimento
 * @Created 22/05/2024 - 17:31
 */
@Component
public class TaskDTOConverter {
    public TaskDTO convert(Task task) {
        return Optional.ofNullable(task)
                .map(TaskDTO::new).orElse(null);
    }

    public Task convert(TaskDTO taskDTO) {
        return Optional.ofNullable(taskDTO)
                .map(source -> Task.builder()
                        .id(source.getId())
                        .title(source.getTitle())
                        .description(source.getDescription())
                        .priority(source.getPriority())
                        .state(source.getState())
                        .build()
                ).orElse(null);
    }

    public Task converter(String id, String title, String description, int priority, TaskState state) {
        return Task.builder()
                .id(id)
                .title(title)
                .description(description)
                .priority(priority)
                .state(state)
                .build();
    }

    public List<TaskDTO> convertList(List<Task> taskList) {
        return Optional.ofNullable(taskList)
                .map(array -> array.stream()
                        .map(this::convert).toList())
                .orElse(new ArrayList<>());
    }

}
