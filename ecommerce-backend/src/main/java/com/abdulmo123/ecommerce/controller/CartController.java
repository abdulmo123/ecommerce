package com.abdulmo123.ecommerce.controller;

import com.abdulmo123.ecommerce.exception.OrderNotFoundException;
import com.abdulmo123.ecommerce.model.Cart;
import com.abdulmo123.ecommerce.model.Product;
import com.abdulmo123.ecommerce.repository.CartRepository;
import com.abdulmo123.ecommerce.repository.OrderRepository;
import com.abdulmo123.ecommerce.service.CartService;
import com.abdulmo123.ecommerce.service.OrderService;
import com.abdulmo123.ecommerce.service.ProductService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class CartController {

    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private final CartService cartService;

    private HttpSession httpSession;

    @Autowired
    private final ProductService productService;

    private static final String CART_ID_ATTRIBUTE = "cartId";

    @Autowired
    private OrderRepository orderRepository;
    private OrderService orderService;

    public CartController(CartService cartService, ProductService productService) {
        this.cartService = cartService;
        this.productService = productService;
    }

    @GetMapping("/carts/all")
    public ResponseEntity<List<Cart>> getAllCarts() {
        List<Cart> carts = cartService.getAllCarts();
        return new ResponseEntity<>(carts, HttpStatus.OK);
    }

    @GetMapping("/carts/find/{id}")
    public ResponseEntity<Cart> getCartById (@PathVariable("id") Long id) {
        Cart cart = cartService.findCartById(id);
        return new ResponseEntity<>(cart, HttpStatus.OK);
    }

    /*@PostMapping("/carts/add")
    public ResponseEntity<Cart> addCart() {
        Cart newCart = cartService.createCart();
        return new ResponseEntity<>(newCart, HttpStatus.CREATED);
    }*/

    @PostMapping("/carts/add")
    public ResponseEntity<Cart> addCart(@RequestBody Cart cart) {
        Cart newCart = cartService.addCart(cart);
        return new ResponseEntity<>(newCart, HttpStatus.CREATED);
    }

    @PutMapping("/carts/update/{id}")
    public void updateCart(@PathVariable ("id") Long id, @RequestBody Cart cart) {
        cartService.updateCategory(id, cart);
    }

    @DeleteMapping("/carts/delete/{id}")
    public void deleteCart(@PathVariable("id") Long id) {
        if (!cartService.findCartById(id).getCartProducts().isEmpty()) {
            cartService.findCartById(id).getCartProducts().clear();
        }
        cartService.deleteCart(id);
    }

    @PostMapping("/orders/{orderId}/carts/add/{cartId}")
    public ResponseEntity<Cart> addCartToOrder(@PathVariable(value="orderId") Long orderId, @PathVariable(value="cartId") Long cartId) {
        Cart newCartToOrder = cartService.findCartById(cartId);
        Cart finalNewCartToOrder = newCartToOrder;

        if (cartRepository.existsById(cartId)) {
            newCartToOrder = orderRepository.findById(orderId)
                    .map(order -> {
                        order.setCart(finalNewCartToOrder);
                        return cartService.addCart(finalNewCartToOrder);
                    })
                    .orElseThrow(() -> new OrderNotFoundException("Order with id: " + orderId + " not found!"));
        }
        else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(newCartToOrder,HttpStatus.CREATED);
    }

    @PostMapping("/carts/{cartId}/products/add/{productId}")
    public ResponseEntity<Cart> addProductToCart (@PathVariable(value="cartId") Long cartId, @PathVariable(value="productId") Long productId) {
        Cart cart = cartService.addProductToCart(cartId, productId);
        return new ResponseEntity<>(cart, HttpStatus.OK);
    }
}
