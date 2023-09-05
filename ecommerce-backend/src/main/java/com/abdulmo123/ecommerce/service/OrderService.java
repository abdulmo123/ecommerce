package com.abdulmo123.ecommerce.service;

import com.abdulmo123.ecommerce.exception.CartNotFoundException;
import com.abdulmo123.ecommerce.exception.OrderNotFoundException;
import com.abdulmo123.ecommerce.exception.ProductNotFoundException;
import com.abdulmo123.ecommerce.model.Cart;
import com.abdulmo123.ecommerce.model.Order;
import com.abdulmo123.ecommerce.model.Product;
import com.abdulmo123.ecommerce.repository.CartRepository;
import com.abdulmo123.ecommerce.repository.OrderRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
@Transactional
public class OrderService {

    @Autowired
    private final OrderRepository orderRepository;

    @Autowired
    private CartRepository cartRepository;

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
        order.setName(String.valueOf(100000 + new Random().nextLong(999999)));
        order.setCreatedDate(new Date());
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

    public Order addCartToOrder(Long orderId, Long cartId) {
        Optional<Order> optionalOrder = orderRepository.findById(orderId);
        if (optionalOrder.isPresent()) {
            Order order = optionalOrder.get();

            Optional<Cart> optionalCart = cartRepository.findById(cartId);
            if (optionalCart.isPresent()) {
                Cart cart = optionalCart.get();
                order.setCart(cart);

                return orderRepository.save(order);
            }
            else {
                throw new CartNotFoundException("Cart with id: " + cartId + " not found!");
            }
        }
        else {
            throw new OrderNotFoundException("Order with id: " + orderId + " not found!");
        }
    }
}
