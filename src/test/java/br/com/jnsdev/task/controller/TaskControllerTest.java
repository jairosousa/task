package br.com.jnsdev.task.controller;

import br.com.jnsdev.task.controller.converter.TaskDTOConverter;
import br.com.jnsdev.task.controller.converter.TaskInsertDTOConverter;
import br.com.jnsdev.task.controller.dto.TaskDTO;
import br.com.jnsdev.task.controller.dto.TaskInsertDTO;
import br.com.jnsdev.task.model.Task;
import br.com.jnsdev.task.service.TaskService;
import br.com.jnsdev.task.utils.TestUtils;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

/**
 * @Autor Jairo Nascimento
 * @Created 23/05/2024 - 17:30
 */
@SpringBootTest
class TaskControllerTest {

    @InjectMocks
    private TaskController controller;
    @Mock
    private TaskService service;
    @Mock
    private TaskDTOConverter converter;

    @Mock
    private TaskInsertDTOConverter insertDTOConverter;

    @Test
    void controller_mustReturnOk_whenSaveSuccessFully() {
        Task convertedTask = TestUtils.buildValidTask();

        when(converter.convert(any(Task.class))).thenReturn(new TaskDTO());
        when(service.insert(any())).thenReturn(Mono.just(convertedTask));

        WebTestClient client = WebTestClient.bindToController(controller).build();

        client.post()
                .uri("/task")
                .bodyValue(new TaskInsertDTO())
                .exchange()
                .expectStatus().isOk()
                .expectBody(TaskDTO.class);
    }

    @Test
    void controller_mustReturnOk_whenGetPaginatedSuccessfully() {
        when(service.findPaginated(any(), anyInt(), anyInt())).thenReturn(Page.empty());
        WebTestClient client = WebTestClient.bindToController(controller).build();

        client.get()
                .uri("/task")
                .exchange()
                .expectStatus().isOk()
                .expectBody(TaskDTO.class);
    }

    @Test
    void controller_mustReturnNoContent_whenDeleteSuccessfully() {
        String taskId = "any-id";
        when(service.deleteById(taskId)).thenReturn(Mono.empty());
        WebTestClient client = WebTestClient.bindToController(controller).build();

        client.delete()
                .uri("/task/" + taskId)
                .exchange()
                .expectStatus().isNoContent();
    }

}