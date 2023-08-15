package com.abdulmo123.ecommerce.repository;

import com.abdulmo123.ecommerce.model.Cart;
import com.abdulmo123.ecommerce.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
    Optional<Cart> findCartById(Long id);

//    boolean existsCurrentCart(Cart cart);

    void deleteCartById(Long id);
}
