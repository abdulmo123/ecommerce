package com.abdulmo123.ecommerce.service;

import com.abdulmo123.ecommerce.exception.CategoryNotFoundException;
import com.abdulmo123.ecommerce.model.Category;
import com.abdulmo123.ecommerce.repository.CategoryRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class CategoryService {

    @Autowired
    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    public Category findCategoryById(Long id) {
        return categoryRepository.findCategoryById(id)
                .orElseThrow(() -> new CategoryNotFoundException("Category with id: " + id + " not found!"));
    }

    public Category addCategory(Category category) {
        return categoryRepository.save(category);
    }

    public void updateCategory (Long id, Category category) {
        if (categoryRepository.findById(id).get() != null) {
            categoryRepository.save(category);
        }
    }

    public void deleteCategory(Long id) {
        categoryRepository.deleteCategoryById(id);
    }
}
