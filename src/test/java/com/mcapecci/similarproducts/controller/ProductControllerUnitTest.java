package com.mcapecci.similarproducts.controller;

import com.mcapecci.similarproducts.model.ProductDetail;
import com.mcapecci.similarproducts.service.IProductService;
import com.mcapecci.similarproducts.util.MockUtil;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.server.ResponseStatusException;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProductControllerUnitTest {

    @Autowired
    private MockMvc mockMvc;
    @Mock
    private IProductService service;

    @InjectMocks
    ProductController controller;

    @Test
    @DisplayName("Ok - getProductSimilar")
    public void getProductSimilar() throws Exception {
        when(service.getProductSimilar(any())).thenReturn(Set.of(MockUtil.PRODUCT_DETAIL_21, MockUtil.PRODUCT_DETAIL_22));

        ResponseEntity<Set<ProductDetail>> response = controller.getProductSimilar("1");

        assertTrue(response.getStatusCode().is2xxSuccessful());
        assertEquals(2, response.getBody().size());

        verify(service).getProductSimilar(any());
    }

    @Test
    @DisplayName("Exception - getProductSimilar")
    public void getProductSimilar_ResponseStatusException() throws Exception {
        when(service.getProductSimilar("1234")).thenThrow(ResponseStatusException.class);
        assertThrows(ResponseStatusException.class, () -> {
            service.getProductSimilar("1234");
        });

        verify(service).getProductSimilar("1234");
    }
}