package br.com.jnsdev.task.client;

import br.com.jnsdev.task.exception.CepNotFoundException;
import br.com.jnsdev.task.model.Address;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

/**
 * @Autor Jairo Nascimento
 * @Created 03/06/2024 - 17:48
 */
@Component
public class ViaCepClient {
    private static final Logger LOGGER = LoggerFactory.getLogger(ViaCepClient.class);

    private final WebClient client;

    private static final String VIA_CEP_URI = "/{cep}/json";

    public ViaCepClient(WebClient viaCep) {
        this.client = viaCep;
    }

    public Mono<Address> getAddress(String cep) {
        return client
                .get()
                .uri(VIA_CEP_URI, cep)
                .retrieve()
                .bodyToMono(Address.class)
                .onErrorResume(error -> Mono.error(CepNotFoundException::new));
    }

}
