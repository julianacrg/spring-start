package br.com.spring.api.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.math.BigDecimal;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import br.com.spring.api.models.ProductModel;
import br.com.spring.api.repositories.ProductRepository;

@SpringBootTest
public class ProductServiceTest {

    @Autowired
    private ProductService productService;

    @MockBean
    private ProductRepository productRepository;

    @Test
    public void testSaveProduct() {
        ProductModel product = new ProductModel(UUID.randomUUID(), "Product Test", BigDecimal.valueOf(100), "Description", 10);
        Mockito.when(productRepository.save(Mockito.any(ProductModel.class))).thenReturn(product);

        ProductModel savedProduct = productService.save(product);
        assertNotNull(savedProduct);
        assertEquals("Product Test", savedProduct.getName());
    }
}
