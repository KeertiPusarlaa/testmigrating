package com.javatechie.jwt.api.dto;

import java.math.BigDecimal;

public class OrderResponse {

    private final Long id;
    private final Integer userId;
    private final String productCode;
    private final Integer quantity;
    private final BigDecimal totalAmount;

    public OrderResponse(Long id, Integer userId, String productCode, Integer quantity, BigDecimal totalAmount) {
        this.id = id;
        this.userId = userId;
        this.productCode = productCode;
        this.quantity = quantity;
        this.totalAmount = totalAmount;
    }

    public Long getId() {
        return id;
    }

    public Integer getUserId() {
        return userId;
    }

    public String getProductCode() {
        return productCode;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }
}
