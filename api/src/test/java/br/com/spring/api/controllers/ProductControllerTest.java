package br.com.spring.api.controllers;

import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.spring.api.dtos.ProductRecordDto;
import br.com.spring.api.models.ProductModel;
import br.com.spring.api.services.ProductService;

@WebMvcTest(ProductController.class)
public class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;

    private ObjectMapper objectMapper = new ObjectMapper();
    private ProductModel testProduct;

    @BeforeEach
    public void setUp() {
        testProduct = new ProductModel(UUID.randomUUID(), "Test Product", BigDecimal.valueOf(100.0), "Test Description", 10);
    }

    @Test
    public void testGetAllProducts() throws Exception {
        when(productService.findAll()).thenReturn(List.of(testProduct));

        mockMvc.perform(get("/products"))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$[0].name").value("Test Product"));
    }

    @Test
    public void testGetOneProduct() throws Exception {
        UUID productId = testProduct.getIdProduct();
        when(productService.findById(productId)).thenReturn(Optional.of(testProduct));

        mockMvc.perform(get("/products/{id}", productId))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.name").value("Test Product"));
    }

    @Test
    public void testGetOneProductNotFound() throws Exception {
        UUID productId = UUID.randomUUID();
        when(productService.findById(productId)).thenReturn(Optional.empty());

        mockMvc.perform(get("/products/{id}", productId))
               .andExpect(status().isNotFound())
               .andExpect(content().string("Product not found."));
    }

    @Test
    public void testSaveProduct() throws Exception {
        ProductRecordDto productDto = new ProductRecordDto("Test Product", BigDecimal.valueOf(100.0), "Test Description", 10);
        when(productService.save(any(ProductModel.class))).thenReturn(testProduct);

        mockMvc.perform(post("/products")
               .contentType(MediaType.APPLICATION_JSON)
               .content(objectMapper.writeValueAsString(productDto)))
               .andExpect(status().isCreated())
               .andExpect(jsonPath("$.name").value("Test Product"));
    }

    @Test
    public void testUpdateProduct() throws Exception {
        UUID productId = testProduct.getIdProduct();
        ProductRecordDto productDto = new ProductRecordDto("Updated Product", BigDecimal.valueOf(200.0), "Updated Description", 20);
        when(productService.findById(productId)).thenReturn(Optional.of(testProduct));
        when(productService.save(any(ProductModel.class))).thenReturn(testProduct);

        mockMvc.perform(put("/products/{id}", productId)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(productDto)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.name").value("Updated Product"));
    }

    @Test
    public void testUpdateProductNotFound() throws Exception {
        UUID productId = UUID.randomUUID();
        ProductRecordDto productDto = new ProductRecordDto("Updated Product", BigDecimal.valueOf(200.0), "Updated Description", 20);
        when(productService.findById(productId)).thenReturn(Optional.empty());

        mockMvc.perform(put("/products/{id}", productId)
               .contentType(MediaType.APPLICATION_JSON)
               .content(objectMapper.writeValueAsString(productDto)))
               .andExpect(status().isNotFound())
               .andExpect(content().string("Product not found."));
    }

    @Test
    public void testDeleteProduct() throws Exception {
        UUID productId = testProduct.getIdProduct();
        when(productService.findById(productId)).thenReturn(Optional.of(testProduct));
        doNothing().when(productService).delete(testProduct);

        mockMvc.perform(delete("/products/{id}", productId))
               .andExpect(status().isOk())
               .andExpect(content().string("Product deleted successfully."));
    }

    @Test
    public void testDeleteProductNotFound() throws Exception {
        UUID productId = UUID.randomUUID();
        when(productService.findById(productId)).thenReturn(Optional.empty());

        mockMvc.perform(delete("/products/{id}", productId))
               .andExpect(status().isNotFound())
               .andExpect(content().string("Product not found."));
    }
}