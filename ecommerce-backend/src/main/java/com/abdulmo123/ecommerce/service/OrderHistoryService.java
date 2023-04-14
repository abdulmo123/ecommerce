package com.abdulmo123.ecommerce.service;

import com.abdulmo123.ecommerce.exception.OrderHistoryNotFoundException;
import com.abdulmo123.ecommerce.model.OrderHistory;
import com.abdulmo123.ecommerce.repository.OrderHistoryRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class OrderHistoryService {

    @Autowired
    private final OrderHistoryRepository orderHistoryRepository;

    public OrderHistoryService(OrderHistoryRepository orderHistoryRepository) {
        this.orderHistoryRepository = orderHistoryRepository;
    }

    public List<OrderHistory> getAllOrderHistory() {
        return orderHistoryRepository.findAll();
    }

    public OrderHistory findOrderHistoryById(Long id) {
        return orderHistoryRepository.findById(id)
                .orElseThrow(() -> new OrderHistoryNotFoundException
                        ("Order history with id : " + id + " not found!"));
    }

    public OrderHistory addOrderHistory(OrderHistory orderHistory) {
        return orderHistoryRepository.save(orderHistory);
    }

    public void updateOrderHistory(Long id, OrderHistory orderHistory) {
        if (orderHistoryRepository.findById(id) != null) {
            orderHistoryRepository.save(orderHistory);
        }
    }

    public void deleteOrderHistory(Long id) {
        orderHistoryRepository.deleteById(id);
    }
}
