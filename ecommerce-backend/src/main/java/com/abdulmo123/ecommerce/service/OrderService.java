package com.abdulmo123.ecommerce.service;

import com.abdulmo123.ecommerce.exception.OrderNotFoundException;
import com.abdulmo123.ecommerce.model.Order;
import com.abdulmo123.ecommerce.repository.OrderRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class OrderService {

    @Autowired
    private final OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public List<Order> getAllOrders () {
        return orderRepository.findAll();
    }

    public Order findOrderById(Long id) {
        return orderRepository.findOrderById(id)
                .orElseThrow(() -> new OrderNotFoundException("Order with id: " + id + " not found!"));
    }

    public Order addOrder (Order order) {
        return orderRepository.save(order);
    }

    public void updateOrder(Long id, Order order) {
        if (orderRepository.findById(id).get() != null) {
            orderRepository.save(order);
        }
    }

    public void deleteOrder(Long id) {
        orderRepository.deleteOrderById(id);
    }
}
