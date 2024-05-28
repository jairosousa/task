package br.com.jnsdev.task.utils;

import br.com.jnsdev.task.controller.dto.TaskDTO;
import br.com.jnsdev.task.model.Task;
import br.com.jnsdev.task.model.TaskState;

/**
 * @Autor Jairo Nascimento
 * @Created 28/05/2024 - 15:35
 */
public class TestUtils {
    public static Task buildValidTask() {
        return Task.builder()
                .id("123")
                .title("title")
                .description("description")
                .priority(1)
                .state(TaskState.INSERT)
                .build();
    }

    public static TaskDTO buildValidTaskDTO() {
        return new TaskDTO(buildValidTask());
    }
}
