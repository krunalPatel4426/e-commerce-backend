package com.Ecommerce.e_commerce.controller;

import com.Ecommerce.e_commerce.model.Category;
import com.Ecommerce.e_commerce.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/category")
public class CategoryController {
    @Autowired
    CategoryService categoryService;

    @PostMapping("/add")
    public ResponseEntity<Category> addCategory(@RequestBody Category category){
        return categoryService.addCategory(category);
    }

    @GetMapping("/")
    public ResponseEntity<List<Category>> getAllCategories(){
        return categoryService.getAllCategories();
    }
}
