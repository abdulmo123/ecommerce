package com.abdulmo123.ecommerce.controller;

import com.abdulmo123.ecommerce.exception.OrderNotFoundException;
import com.abdulmo123.ecommerce.model.Cart;
import com.abdulmo123.ecommerce.model.CurrentUserDetails;
import com.abdulmo123.ecommerce.model.Product;
import com.abdulmo123.ecommerce.model.User;
import com.abdulmo123.ecommerce.repository.CartRepository;
import com.abdulmo123.ecommerce.repository.OrderRepository;
import com.abdulmo123.ecommerce.service.CartService;
import com.abdulmo123.ecommerce.service.OrderService;
import com.abdulmo123.ecommerce.service.ProductService;
import com.abdulmo123.ecommerce.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

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

    @Autowired
    private UserService userService;

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

    @PostMapping("/carts/add")
    public ResponseEntity<Cart> addCart(@RequestBody Map<String, Long> request) {
        Long userId = request.get("userId");
        User currentUser = userService.findUserById(userId);

        Cart newCart = new Cart();
        newCart.setUser(currentUser);

        Cart savedCart = cartService.addCart(newCart);
        return new ResponseEntity<>(savedCart, HttpStatus.CREATED);
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

    @PostMapping("/carts/{cartId}/products/add/{productId}")
    public ResponseEntity<Cart> addProductToCart (@PathVariable(value="cartId") Long cartId, @PathVariable(value="productId") Long productId) {
        Cart cart = cartService.addProductToCart(cartId, productId);
        return new ResponseEntity<>(cart, HttpStatus.OK);
    }
}
