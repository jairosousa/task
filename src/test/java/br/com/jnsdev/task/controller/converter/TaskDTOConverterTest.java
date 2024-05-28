package br.com.jnsdev.task.controller.converter;

import br.com.jnsdev.task.controller.dto.TaskDTO;
import br.com.jnsdev.task.model.Task;
import br.com.jnsdev.task.model.TaskState;
import br.com.jnsdev.task.utils.TestUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static br.com.jnsdev.task.utils.TestUtils.buildValidTask;
import static br.com.jnsdev.task.utils.TestUtils.buildValidTaskDTO;
import static java.util.Collections.singletonList;
import static org.junit.jupiter.api.Assertions.*;

/**
 * @Autor Jairo Nascimento
 * @Created 28/05/2024 - 15:25
 */
@SpringBootTest
class TaskDTOConverterTest {

    @InjectMocks
    private TaskDTOConverter converter;

    @Test
    void convert_mustReturnTaskDTO_whenInputTask() {
        Task task = buildValidTask();
        TaskDTO dto = converter.convert(task);

        assertEquals(dto.getId(), task.getId());
        assertEquals(dto.getTitle(), task.getTitle());
        assertEquals(dto.getDescription(), task.getDescription());
        assertEquals(dto.getPriority(), task.getPriority());
        assertEquals(dto.getState(), task.getState());

    }

    @Test
    void convert_mustReturnTask_whenInputTaskDTO() {
        TaskDTO taskDTO = buildValidTaskDTO();
        Task task = converter.convert(taskDTO);

        assertEquals(task.getId(), taskDTO.getId());
        assertEquals(task.getTitle(), taskDTO.getTitle());
        assertEquals(task.getDescription(), taskDTO.getDescription());
        assertEquals(task.getPriority(), taskDTO.getPriority());
        assertEquals(task.getState(), taskDTO.getState());
    }

    @Test
    void convert_mustReturnTask_whenInputParameters() {
        String id = "123";
        String title = "title";
        String description = "description";
        int priority = 1;
        TaskState state = TaskState.INSERT;

        Task task = converter.convert(id, title, description, priority, state);

        assertEquals(id, task.getId());
        assertEquals(title, task.getTitle());
        assertEquals(description, task.getDescription());
        assertEquals(priority, task.getPriority());
        assertEquals(state, task.getState());
    }

    @Test
    void convert_mustReturnListTaskDTO_whenInputListTask() {
        List<Task> taskList = singletonList(buildValidTask());
        List<TaskDTO> listtaskDto = converter.convertList(taskList);

        assertEquals(1, listtaskDto.size());
        assertFalse(listtaskDto.isEmpty());
    }
}