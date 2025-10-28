package com.javatechie.jwt.api.service;

import com.javatechie.jwt.api.dto.OrderRequest;
import com.javatechie.jwt.api.dto.OrderResponse;
import com.javatechie.jwt.api.entity.Order;
import com.javatechie.jwt.api.repository.OrderRepository;
import org.springframework.stereotype.Service;

@Service
public class OrderService {

    private final OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public OrderResponse createOrder(OrderRequest request) {
        Order order = new Order();
        order.setUserId(request.getUserId());
        order.setProductCode(request.getProductCode());
        order.setQuantity(request.getQuantity());
        order.setTotalAmount(request.getTotalAmount());

        Order saved = orderRepository.save(order);
        return new OrderResponse(
                saved.getId(),
                saved.getUserId(),
                saved.getProductCode(),
                saved.getQuantity(),
                saved.getTotalAmount()
        );
    }
}
