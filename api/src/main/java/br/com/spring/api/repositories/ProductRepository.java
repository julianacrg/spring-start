package br.com.spring.api.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.spring.api.models.ProductModel;

@Repository
public interface ProductRepository extends JpaRepository<ProductModel, UUID>{
}
