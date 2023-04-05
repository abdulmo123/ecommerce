package com.abdulmo123.ecommerce.controller;

import com.abdulmo123.ecommerce.model.Cart;
import com.abdulmo123.ecommerce.repository.CartRepository;
import com.abdulmo123.ecommerce.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/carts")
public class CartController {

    @Autowired
    private CartRepository cartRepository;
    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<Cart>> getAllCarts() {
        List<Cart> carts = cartService.getAllCarts();
        return new ResponseEntity<>(carts, HttpStatus.OK);
    }

    @GetMapping("find/{id}")
    public ResponseEntity<Cart> getCartById (@PathVariable("id") Long id) {
        Cart cart = cartService.findCartById(id);
        return new ResponseEntity<>(cart, HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<Cart> addCart(@RequestBody Cart cart) {
        Cart newCart = cartService.addCart(cart);
        return new ResponseEntity<>(newCart, HttpStatus.CREATED);
    }

    @PutMapping("/update/{id}")
    public void updateCart(@PathVariable ("id") Long id, @RequestBody Cart cart) {
        cartService.updateCategory(id, cart);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteCart(@PathVariable("id") Long id) {
        if (!cartService.findCartById(id).getCartProducts().isEmpty()) {
            cartService.findCartById(id).getCartProducts().clear();
        }
        cartService.deleteCart(id);
    }
}
