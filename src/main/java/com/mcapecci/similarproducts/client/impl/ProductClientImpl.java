package com.mcapecci.similarproducts.client.impl;

import com.mcapecci.similarproducts.client.IProductClient;
import com.mcapecci.similarproducts.model.ProductDetail;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.List;

@Slf4j
@Service
public class ProductClientImpl implements IProductClient<ProductDetail> {

    @Value("${api.client.product.base-url}")
    private String base_url;
    @Value("${api.client.product.getSimilarProductIds}")
    private String productSimilarIdsPath;
    @Value("${api.client.product.getProductById}")
    private String productByIdPath;
    @Autowired
    WebClient client;


    @Override
    public List<String> getProductSimilarIds(String id) {
        final String url = String.format("%s%s", base_url, productSimilarIdsPath);
        log.debug(url);
        Mono<String[]> response = client.get().uri(productSimilarIdsPath, id).retrieve()
                .onStatus(status -> status.equals(HttpStatus.NOT_FOUND), clientResponse -> {
                    final String msg = String.format("Executing endpoint %s with productId %s and status code %s", url,
                            id, clientResponse.statusCode());
                    log.error(msg);
                    throw new ResponseStatusException(HttpStatus.NOT_FOUND, HttpStatus.NOT_FOUND.getReasonPhrase());
                }).onStatus(HttpStatus::is5xxServerError, clientResponse -> {
                    final String msg = String.format("Executing endpoint %s with productId %s and status code %s", url,
                            id, clientResponse.statusCode());
                    log.error(msg);
                    throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE, HttpStatus.SERVICE_UNAVAILABLE.getReasonPhrase());
                }).bodyToMono(String[].class);
        return Arrays.asList(response.block());
    }

    @Override
    public ProductDetail getProductById(String id) {
        final String url = String.format("%s%s", base_url, productByIdPath);
        log.debug(url);
        Mono<ProductDetail> response = client.get().uri(productByIdPath, id).retrieve()
                .onStatus(status -> status.equals(HttpStatus.NOT_FOUND), clientResponse -> {
                    final String msg = String.format("Executing endpoint %s with productId %s and status code %s", url,
                            id, clientResponse.statusCode());
                    log.error(msg);
                    return Mono.empty();
//                    return Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, HttpStatus.NOT_FOUND.getReasonPhrase()));
                }).onStatus(HttpStatus::is5xxServerError, clientResponse -> {
                    final String msg = String.format("Executing endpoint %s with productId %s and status code %s", url,
                            id, clientResponse.statusCode());
                    log.error(msg);
                    return Mono.empty();
//                    return Mono.error(new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE, HttpStatus.SERVICE_UNAVAILABLE.getReasonPhrase()));
                }).bodyToMono(ProductDetail.class);
        return response.block();
    }
}
