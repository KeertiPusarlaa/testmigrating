package com.javatechie.jwt.api.repository;

import com.javatechie.jwt.api.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
