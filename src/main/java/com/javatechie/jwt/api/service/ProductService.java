package com.javatechie.jwt.api.service;

import com.javatechie.jwt.api.dto.PagedResponse;
import com.javatechie.jwt.api.dto.ProductResponse;
import com.javatechie.jwt.api.entity.Product;
import com.javatechie.jwt.api.repository.ProductRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public PagedResponse<ProductResponse> getProducts(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("id").ascending());
        Page<Product> productPage = productRepository.findAll(pageable);

        List<ProductResponse> content = productPage.getContent().stream()
                .map(product -> new ProductResponse(
                        product.getId(),
                        product.getName(),
                        product.getDescription(),
                        product.getPrice()))
                .collect(Collectors.toList());

        return new PagedResponse<>(
                productPage.getNumber(),
                productPage.getSize(),
                productPage.getTotalElements(),
                productPage.getTotalPages(),
                content
        );
    }
}
