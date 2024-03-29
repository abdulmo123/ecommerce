package com.abdulmo123.ecommerce.controller;

import com.abdulmo123.ecommerce.model.Cart;
import com.abdulmo123.ecommerce.model.Order;
import com.abdulmo123.ecommerce.repository.OrderRepository;
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

    @PostMapping("/orders/{orderId}/carts/add/{cartId}")
    public ResponseEntity<Order> addCartToOrder (@PathVariable(value="orderId") Long orderId, @PathVariable(value="cartId") Long cartId) {
        Order order = orderService.addCartToOrder(orderId, cartId);
        return new ResponseEntity<>(order, HttpStatus.OK);
    }
}
