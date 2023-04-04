package com.abdulmo123.ecommerce.service;

import com.abdulmo123.ecommerce.exception.CartNotFoundException;
import com.abdulmo123.ecommerce.exception.CategoryNotFoundException;
import com.abdulmo123.ecommerce.exception.ProductNotFoundException;
import com.abdulmo123.ecommerce.model.Cart;
import com.abdulmo123.ecommerce.model.Category;
import com.abdulmo123.ecommerce.model.Product;
import com.abdulmo123.ecommerce.repository.CartRepository;
import com.abdulmo123.ecommerce.repository.CategoryRepository;
import com.abdulmo123.ecommerce.repository.ProductRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class ProductService {

    @Autowired
    private final ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private CartRepository cartRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Product findProductById(Long id) {
        return productRepository.findProductById(id)
                .orElseThrow(() -> new ProductNotFoundException("Product with id: " + id + " doesn't exist!"));
    }

    public Product addProduct (Product product) {
//        product.setCategory(new Category());
        return productRepository.save(product);
    }

    public void updateProduct(Product product, Long id) {
        if (productRepository.findById(id).get() != null) {
            productRepository.save(product);
        }
    }

    public void deleteProduct(Long id) {
        productRepository.deleteProductById(id);
    }

    public List<Product> findProductByCategoryId(Long categoryId) {
        Category category = this.categoryRepository.findById(categoryId)
                .orElseThrow(() -> new CategoryNotFoundException("Category with id: " + categoryId + " was not found!"));

        return this.productRepository.findProductByCategory(category);
    }

    public List<Product> findProductByCartId(Long cartId) {
        Cart cart = this.cartRepository.findById(cartId)
                .orElseThrow(() -> new CartNotFoundException("Cart with id: " + cartId + " was not found!"));

        return this.productRepository.findProductByCart(cart);
    }
}
