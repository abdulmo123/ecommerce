package com.abdulmo123.ecommerce.service;

import com.abdulmo123.ecommerce.exception.CartNotFoundException;
import com.abdulmo123.ecommerce.model.Cart;
import com.abdulmo123.ecommerce.repository.CartRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class CartService {

    @Autowired
    private final CartRepository cartRepository;

    public CartService (CartRepository cartRepository) {
        this.cartRepository = cartRepository;
    }

    public List<Cart> getAllCarts() {
        return cartRepository.findAll();
    }

    public Cart findCartById(Long id) {
        return cartRepository.findCartById(id)
                .orElseThrow(() -> new CartNotFoundException("Cart with id: " + id + " not found!"));
    }

    public Cart addCart(Cart cart) {
        return cartRepository.save(cart);
    }

    public void updateCategory(Long id, Cart cart) {
        if (cartRepository.findById(id).get() != null) {
            cartRepository.save(cart);
        }
    }

    public void deleteCart(Long id) {
        cartRepository.deleteCartById(id);
    }
}
