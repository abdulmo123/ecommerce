package com.abdulmo123.ecommerce.repository;

import com.abdulmo123.ecommerce.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    Optional<Category> findCategoryById(Long id);

    void deleteCategoryById(Long id);
}
