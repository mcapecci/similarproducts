package com.mcapecci.similarproducts.controller;

import com.mcapecci.similarproducts.api.ProductApi;
import com.mcapecci.similarproducts.model.ProductDetail;
import com.mcapecci.similarproducts.service.IProductService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@Tag(name = "Product")
@RestController
@Slf4j
public class ProductController implements ProductApi {
    @Autowired
    private IProductService service;
    @Override
    public ResponseEntity<Set<ProductDetail>> getProductSimilar(String productId) {
        log.debug("Entering ProductController getProductSimilar [productId]: {}", productId);

        Set<ProductDetail> response = service.getProductSimilar(productId);

        log.debug("Leaving ProductController getProductSimilar");

        return ResponseEntity.ok(response);
    }
}
