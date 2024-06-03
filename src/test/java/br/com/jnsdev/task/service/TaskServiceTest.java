package br.com.jnsdev.task.service;

import br.com.jnsdev.task.model.Task;
import br.com.jnsdev.task.repository.TaskCustomRepository;
import br.com.jnsdev.task.repository.TaskRepository;
import br.com.jnsdev.task.utils.TestUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * @Autor Jairo Nascimento
 * @Created 29/05/2024 - 16:31
 */
@SpringBootTest
class TaskServiceTest {

    @InjectMocks
    private TaskService taskService;

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private TaskCustomRepository customRepository;

    @Test
    void service_mustReturnTask_whenInsertSuccessfully() {
        Task task = TestUtils.buildValidTask();

        when(taskRepository.save(any(Task.class))).thenReturn(Mono.just(task));

        StepVerifier.create(taskService.insert(task))
                .then(() -> verify(taskRepository, times(1))
                        .save(any(Task.class)))
                .expectNext(task)
                .expectComplete();
    }

    @Test
    void service_mustReturnVoid_deleteTaskSuccessfully() {

        when(taskRepository.deleteById(anyString())).thenReturn(Mono.empty());

        StepVerifier.create(taskService.deleteById("someId"))
                .then(() -> verify(taskRepository, times(1))
                        .deleteById(anyString()))
                .verifyComplete();
    }

    @Test
    void service_mustReturnTaskPage_whenFindPaginated() {
        Task task = TestUtils.buildValidTask();

        when(customRepository.findPaginated(any(), anyInt(), anyInt())).thenReturn(Mono.just(Page.empty()));

        Mono<Page<Task>> result = taskService.findPaginated(task, 0, 10);

        assertNotNull(result);
        verify(customRepository, times(1)).findPaginated(any(), anyInt(), anyInt());
    }

}