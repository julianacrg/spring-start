package br.com.spring.api.services;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.spring.api.models.ProductModel;
import br.com.spring.api.repositories.ProductRepository;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public List<ProductModel> findAll() {
        return productRepository.findAll();
    }

    public Optional<ProductModel> findById(UUID id) {
        return productRepository.findById(id);
    }

    public ProductModel save(ProductModel product) {
        return productRepository.save(product);
    }

    public ProductModel update(ProductModel product) {
        return productRepository.save(product);
    }

    public void delete(ProductModel product) {
        productRepository.delete(product);
    }
}