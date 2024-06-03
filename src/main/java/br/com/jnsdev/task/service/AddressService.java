package br.com.jnsdev.task.service;

import br.com.jnsdev.task.client.ViaCepClient;
import br.com.jnsdev.task.exception.CepNotFoundException;
import br.com.jnsdev.task.model.Address;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

/**
 * @Autor Jairo Nascimento
 * @Created 03/06/2024 - 18:03
 */
@Service
public class AddressService {
    private static final Logger LOGGER = LoggerFactory.getLogger(AddressService.class);
    private final ViaCepClient viaCepClient;

    public AddressService(ViaCepClient viaCepClient) {
        this.viaCepClient = viaCepClient;
    }

    public Mono<Address> getAddress(String cep) {
        return Mono.just(cep)
                .doOnNext(it -> LOGGER.info("Getting address to cep {}", cep))
                .flatMap(viaCepClient::getAddress)
                .doOnError(it -> Mono.error(CepNotFoundException::new));
    }

}
