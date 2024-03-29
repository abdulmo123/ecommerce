package com.abdulmo123.ecommerce.controller;

import com.abdulmo123.ecommerce.exception.CartNotFoundException;
import com.abdulmo123.ecommerce.exception.CategoryNotFoundException;
import com.abdulmo123.ecommerce.exception.ProductNotFoundException;
import com.abdulmo123.ecommerce.model.Cart;
import com.abdulmo123.ecommerce.model.Product;
import com.abdulmo123.ecommerce.repository.CartRepository;
import com.abdulmo123.ecommerce.repository.CategoryRepository;
import com.abdulmo123.ecommerce.repository.ProductRepository;
import com.abdulmo123.ecommerce.service.CartService;
import com.abdulmo123.ecommerce.service.CategoryService;
import com.abdulmo123.ecommerce.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api")
public class ProductController {

    @Autowired
    private ProductRepository productRepository;
    private final ProductService productService;

    @Autowired
    private CategoryRepository categoryRepository;
    private CategoryService categoryService;

    @Autowired
    private CartRepository cartRepository;
    private CartService cartService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/products/all")
    public ResponseEntity<List<Product>> getAllProducts () {
        List<Product> products = productService.getAllProducts();
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @GetMapping("/categories/{categoryId}/products/all")
    public ResponseEntity<List<Product>> getAllProductsByCategoryId( @PathVariable (value = "categoryId") Long categoryId) {
        if (!categoryRepository.existsById(categoryId)) {
            categoryRepository.findCategoryById(categoryId);
        }

        List<Product> products = productService.findProductByCategoryId(categoryId);
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @PostMapping("/categories/{categoryId}/products/add")
    public ResponseEntity<Product> addProductToCategory (@PathVariable (value = "categoryId") Long categoryId, @RequestBody Product product) {

        Product newProduct = categoryRepository.findById(categoryId)
                .map(category -> {
                    product.setCategory(category);
                    return productService.addProduct(product);
                })
                .orElseThrow(() -> new CategoryNotFoundException("Category with id: " + categoryId + " not found!"));

        return new ResponseEntity<>(newProduct, HttpStatus.CREATED);
    }

    @PutMapping("/products/update/{id}")
    public void updateProduct (@RequestBody Product product, @PathVariable ("id") Long id) {
        Product updatedProduct = productService.findProductById(id);

        updatedProduct.setName(product.getName());
        updatedProduct.setDescription(product.getDescription());
        updatedProduct.setPrice(product.getPrice());
        updatedProduct.setPicture(product.getPicture());

        productService.updateProduct(updatedProduct, id);
    }

    @GetMapping("/products/find/{id}")
    public ResponseEntity<Product> getProductById (@PathVariable ("id") Long id) throws ProductNotFoundException {
        Product product = productService.findProductById(id);
        return new ResponseEntity<>(product, HttpStatus.OK);
    }

    @DeleteMapping("/products/delete/{id}")
    public void deleteProduct(@PathVariable ("id") Long id) {
        productService.deleteProduct(id);
    }

    @GetMapping("/carts/{cartId}/products/all")
    public ResponseEntity<List<Product>> getAllProductsByCartId(@PathVariable (value = "cartId") Long cartId) {
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new CartNotFoundException("Cart with id: " + cartId + " not found!"));

        List<Product> products = new ArrayList<>();
        products.addAll(cart.getCartProducts());

        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @DeleteMapping("/carts/{cartId}/products/delete")
    public ResponseEntity<List<Product>> clearProductsFromCart (@PathVariable (value="cartId") Long cartId) {
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new CartNotFoundException("Cart with id: " + cartId + " not found!"));

        List<Product> cartProducts = cart.getCartProducts();
        cartProducts.clear();
        cart.setCartProducts(cartProducts);
        cartRepository.save(cart);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/carts/{cartId}/products/delete/{productId}")
    public ResponseEntity<List<Product>> removeProductByIdFromCart (@PathVariable (value="cartId") Long cartId, @PathVariable(value="productId") Long productId) {
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new CartNotFoundException("Cart with id: " + cartId + " not found!"));

        Product product = productService.findProductById(productId);

        List<Product> cartProducts = cart.getCartProducts();
        for (int i = 0; i < cartProducts.size(); i++) {
            if (Objects.equals(cartProducts.get(i).getId(), productId)) {
                cartProducts.remove(i);
            }
        }

        cart.setCartProducts(cartProducts);
        cartRepository.save(cart);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
