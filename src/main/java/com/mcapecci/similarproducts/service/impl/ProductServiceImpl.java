package com.mcapecci.similarproducts.service.impl;

import com.mcapecci.similarproducts.client.impl.ProductClientImpl;
import com.mcapecci.similarproducts.model.ProductDetail;
import com.mcapecci.similarproducts.service.IProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ProductServiceImpl implements IProductService {
    @Autowired
    ProductClientImpl client;

    @Override
    public Set<ProductDetail> getProductSimilar(String productId) {
        log.debug("Entering ProductService getProductSimilar");
        List<String> ids = client.getProductSimilarIds(productId);

        return ids.parallelStream().map(id -> client.getProductById(id)).filter(p -> Objects.nonNull(p.getId()))
                .collect(Collectors.toCollection(LinkedHashSet::new));
    }
}
