package com.javatechie.jwt.api.repository;

import com.javatechie.jwt.api.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
