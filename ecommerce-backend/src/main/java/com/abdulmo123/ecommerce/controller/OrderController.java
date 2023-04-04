package com.abdulmo123.ecommerce.controller;

import com.abdulmo123.ecommerce.repository.OrderRepository;
import com.abdulmo123.ecommerce.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;

public class OrderController {

    @Autowired
    private OrderRepository orderRepository;
    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

}
