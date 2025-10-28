package com.javatechie.jwt.api.dto;

import java.math.BigDecimal;

public class ProductResponse {
    private final Long id;
    private final String name;
    private final String description;
    private final BigDecimal price;

    public ProductResponse(Long id, String name, String description, BigDecimal price) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public BigDecimal getPrice() {
        return price;
    }
}
