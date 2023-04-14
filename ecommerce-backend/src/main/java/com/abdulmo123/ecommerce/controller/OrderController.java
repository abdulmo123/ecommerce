package com.abdulmo123.ecommerce.controller;

import com.abdulmo123.ecommerce.exception.OrderNotFoundException;
import com.abdulmo123.ecommerce.model.Order;
import com.abdulmo123.ecommerce.repository.OrderHistoryRepository;
import com.abdulmo123.ecommerce.repository.OrderRepository;
import com.abdulmo123.ecommerce.service.OrderHistoryService;
import com.abdulmo123.ecommerce.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class OrderController {

    @Autowired
    private OrderRepository orderRepository;
    private final OrderService orderService;

    @Autowired
    private OrderHistoryRepository orderHistoryRepository;
    private OrderHistoryService orderHistoryService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/orders/all")
    public ResponseEntity<List<Order>> getAllOrders () {
        List<Order> orders = orderService.getAllOrders();
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }

    @GetMapping("/orders/find/{id}")
    public ResponseEntity<Order> getOrderById(@PathVariable("id") Long id) {
        Order order = orderService.findOrderById(id);
        return new ResponseEntity<> (order, HttpStatus.OK);
    }

    @PostMapping("/orders/add")
    public ResponseEntity<Order> addOrder(@RequestBody Order order) {
        Order newOrder = orderService.addOrder(order);
        return new ResponseEntity<>(newOrder, HttpStatus.CREATED);
    }

    @PutMapping("/orders/update/{id}")
    public void updateOrder (@PathVariable("id") Long id, Order order) {
        orderService.updateOrder(id, order);
    }

    @DeleteMapping("/orders/delete/{id}")
    public void deleteOrder(@PathVariable("id") Long id) {
        orderService.deleteOrder(id);
    }

    @PostMapping("orderhistory/{orderHistoryId}/orders/add/{orderId}")
    public ResponseEntity<Order> addOrderToOrderHistory(@PathVariable(value="orderHistoryId") Long orderHistoryId, @PathVariable(value="orderId") Long orderId) {

        Order newOrderToHistory = orderService.findOrderById(orderId);
        Order finalNewOrderToHistory = newOrderToHistory;

        if (orderRepository.existsById(orderId)) {
            newOrderToHistory = orderHistoryRepository.findById(orderHistoryId)
                    .map(orderHistory -> {
                        orderHistory.getOrderHistory().add(finalNewOrderToHistory);
                        return orderService.addOrder(finalNewOrderToHistory);
                    })
                    .orElseThrow(() -> new OrderNotFoundException("Order with id: " + orderId + " not found!"));
        }

        else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(newOrderToHistory,HttpStatus.CREATED);
    }

}
