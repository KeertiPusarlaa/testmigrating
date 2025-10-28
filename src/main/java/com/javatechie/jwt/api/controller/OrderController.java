package com.javatechie.jwt.api.controller;

import com.javatechie.jwt.api.dto.OrderRequest;
import com.javatechie.jwt.api.dto.OrderResponse;
import com.javatechie.jwt.api.service.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public ResponseEntity<OrderResponse> createOrder(@Valid @RequestBody OrderRequest request,
                                                     UriComponentsBuilder uriComponentsBuilder) {
        OrderResponse created = orderService.createOrder(request);
        URI location = uriComponentsBuilder.path("/api/orders/{id}")
                .buildAndExpand(created.getId())
                .toUri();
        return ResponseEntity.created(location).body(created);
    }
}
