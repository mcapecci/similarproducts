package com.mcapecci.similarproducts.client.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;


@Configuration
public class WebClientConfig {

    @Value("${api.client.product.base-url}")
    private String base_url;
    @Bean
    public WebClient buildWebClient(){
        return WebClient.builder().baseUrl(base_url)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();
    }
}
