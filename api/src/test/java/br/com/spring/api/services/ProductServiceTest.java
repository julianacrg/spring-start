package br.com.spring.api.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import br.com.spring.api.models.ProductModel;
import br.com.spring.api.repositories.ProductRepository;

@SpringBootTest
public class ProductServiceTest {

    @InjectMocks
    private ProductService productService;

    @Mock
    private ProductRepository productRepository;

    private ProductModel testProduct;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        testProduct = new ProductModel(UUID.randomUUID(), "Test Product", BigDecimal.valueOf(100.0), "Test Description", 10);
    }

    @Test
    public void testFindAll() {
        when(productRepository.findAll()).thenReturn(List.of(testProduct));

        List<ProductModel> products = productService.findAll();

        assertEquals(1, products.size());
        assertEquals("Test Product", products.get(0).getName());
        verify(productRepository, times(1)).findAll();
    }

    @Test
    public void testFindById() {
        UUID productId = testProduct.getIdProduct();
        when(productRepository.findById(productId)).thenReturn(Optional.of(testProduct));

        Optional<ProductModel> product = productService.findById(productId);

        assertTrue(product.isPresent());
        assertEquals("Test Product", product.get().getName());
        verify(productRepository, times(1)).findById(productId);
    }

    @Test
    public void testSave() {
        when(productRepository.save(testProduct)).thenReturn(testProduct);

        ProductModel savedProduct = productService.save(testProduct);

        assertNotNull(savedProduct);
        assertEquals("Test Product", savedProduct.getName());
        verify(productRepository, times(1)).save(testProduct);
    }

    @Test
    public void testUpdate() {
        when(productRepository.save(testProduct)).thenReturn(testProduct);

        ProductModel updatedProduct = productService.update(testProduct);

        assertNotNull(updatedProduct);
        assertEquals("Test Product", updatedProduct.getName());
        verify(productRepository, times(1)).save(testProduct);
    }

    @Test
    public void testDelete() {
        doNothing().when(productRepository).delete(testProduct);

        productService.delete(testProduct);

        verify(productRepository, times(1)).delete(testProduct);
    }
}
