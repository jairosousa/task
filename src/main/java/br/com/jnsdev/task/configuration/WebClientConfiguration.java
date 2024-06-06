package br.com.jnsdev.task.configuration;

import io.netty.resolver.DefaultAddressResolverGroup;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.DefaultUriBuilderFactory;
import reactor.netty.http.client.HttpClient;

/**
 * @Autor Jairo Nascimento
 * @Created 03/06/2024 - 17:55
 */
@Configuration
public class WebClientConfiguration {
    @Bean
    public WebClient viaCep(WebClient.Builder builder,
                            @Value("${via.cep.url}") String url) {
        return getWebClient(builder, url);
    }

    private WebClient getWebClient(WebClient.Builder builder, String url) {
        HttpClient httpClient = HttpClient.create().resolver(DefaultAddressResolverGroup.INSTANCE);
        return  WebClient.builder()
                .clientConnector(new ReactorClientHttpConnector(httpClient))
                .uriBuilderFactory(new DefaultUriBuilderFactory(url))
                .build();
    }

}
