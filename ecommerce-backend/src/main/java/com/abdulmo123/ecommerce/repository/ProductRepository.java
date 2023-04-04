package com.abdulmo123.ecommerce.repository;

import com.abdulmo123.ecommerce.model.Cart;
import com.abdulmo123.ecommerce.model.Category;
import com.abdulmo123.ecommerce.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    void deleteProductById(Long id);
    Optional<Product> findProductById(Long id);
    List<Product> findProductByCategory (Category category);
//    List<Product> findProductByCart (Cart cart);
}
