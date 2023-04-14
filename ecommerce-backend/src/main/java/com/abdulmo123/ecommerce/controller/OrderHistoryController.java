package com.abdulmo123.ecommerce.controller;

import com.abdulmo123.ecommerce.model.Order;
import com.abdulmo123.ecommerce.model.OrderHistory;
import com.abdulmo123.ecommerce.service.OrderHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value="/api/orderhistory")
public class OrderHistoryController {

    @Autowired
    private OrderHistoryService orderHistoryService;

    @GetMapping("/all")
    public ResponseEntity<List<OrderHistory>> getAllOrderHistory() {
        List<OrderHistory> orderHistory = orderHistoryService.getAllOrderHistory();

        return new ResponseEntity<>(orderHistory, HttpStatus.OK);
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<OrderHistory> getOrderHistoryById(@PathVariable("id") Long id) {
        OrderHistory orderHistory = orderHistoryService.findOrderHistoryById(id);
        return new ResponseEntity<>(orderHistory, HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<OrderHistory> addOrderHistory(@RequestBody OrderHistory orderHistory) {
        OrderHistory newOrderHistory = orderHistoryService.addOrderHistory(orderHistory);
        return new ResponseEntity<>(newOrderHistory, HttpStatus.CREATED);
    }

    @PutMapping("/update/{id}")
    public void updateOrderHistory(@PathVariable("id") Long id, @RequestBody OrderHistory orderHistory) {
        orderHistoryService.updateOrderHistory(id, orderHistory);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteOrderHistory(@PathVariable("id") Long id) {
        if (!orderHistoryService.findOrderHistoryById(id).getOrderHistory().isEmpty()) {
            orderHistoryService.findOrderHistoryById(id).getOrderHistory().clear();
        }

        orderHistoryService.deleteOrderHistory(id);
    }
}
