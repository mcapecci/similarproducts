package com.mcapecci.similarproducts.client;

import java.util.List;

public interface IProductClient<T> {

    /**
     * Returns the similar products to a given one ordered by similarity
     * @param id
     * @return
     */
    List<String> getProductSimilarIds(String id);

    /**
     * Returns the product detail for a given productId
     * @param id
     * @return
     */
    T getProductById(String id);

}
