package com.abdulmo123.ecommerce.service;

import com.abdulmo123.ecommerce.exception.CartNotFoundException;
import com.abdulmo123.ecommerce.exception.ProductNotFoundException;
import com.abdulmo123.ecommerce.model.Cart;
import com.abdulmo123.ecommerce.model.Product;
import com.abdulmo123.ecommerce.repository.CartRepository;
import com.abdulmo123.ecommerce.repository.ProductRepository;
import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CartService {

    @Autowired
    private final CartRepository cartRepository;

    @Autowired
    private ProductRepository productRepository;

    public CartService(CartRepository cartRepository) {
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

    public Long createCart() {
        Cart newCart = new Cart();
        Cart savedCart = cartRepository.save(newCart);
        return savedCart.getId();
    }

    public Cart addProductToCart(Long cartId, Long productId) {
        Optional<Cart> optionalCart = cartRepository.findById(cartId);
        if (optionalCart.isPresent()) {
            Cart cart = optionalCart.get();

            Optional<Product> optionalProduct = productRepository.findById(productId);
            if (optionalProduct.isPresent()) {
                Product product = optionalProduct.get();
                cart.getCartProducts().add(product);

                return cartRepository.save(cart);
            }
            else {
                throw new ProductNotFoundException("Product with id: " + productId + " not found!");
            }
        }
        else {
            throw new CartNotFoundException("Cart with id: " + cartId + " not found!");
        }
    }
}
