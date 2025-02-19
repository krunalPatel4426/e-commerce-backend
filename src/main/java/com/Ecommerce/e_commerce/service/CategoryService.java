package com.Ecommerce.e_commerce.service;

import com.Ecommerce.e_commerce.model.Category;
import com.Ecommerce.e_commerce.repo.CategoryRepository;
import jakarta.persistence.Access;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {
    @Autowired
    CategoryRepository categoryRepository;
    public ResponseEntity<Category> addCategory(Category category) {
        return ResponseEntity.ok(categoryRepository.save(category));
    }

    public ResponseEntity<List<Category>> getAllCategories(){
        return ResponseEntity.ok(categoryRepository.findAll());
    }

    public Category getByName(String category) {
        return categoryRepository.findByName(category);
    }
}
