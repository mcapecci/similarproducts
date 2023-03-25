package com.mcapecci.similarproducts.service;

import com.mcapecci.similarproducts.model.ProductDetail;

import java.util.Set;

public interface IProductService {
    Set<ProductDetail> getProductSimilar(String productId);
}
