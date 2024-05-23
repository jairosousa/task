package br.com.jnsdev.task.controller;

import br.com.jnsdev.task.controller.converter.TaskDTOConverter;
import br.com.jnsdev.task.controller.dto.TaskDTO;
import br.com.jnsdev.task.model.Task;
import br.com.jnsdev.task.service.TaskService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

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

    @Test
    void controller_mustReturnOk_whenSaveSuccessFully() {

        Mockito.when(converter.convert(Mockito.any(Task.class))).thenReturn(new TaskDTO());
        Mockito.when(service.insert(Mockito.any())).thenReturn(Mono.just(new Task()));

        WebTestClient client = WebTestClient.bindToController(controller).build();

        client.post()
                .uri("/task")
                .bodyValue(new Task())
                .exchange()
                .expectStatus().isOk()
                .expectBody(Task.class);
    }

}