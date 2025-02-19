package com.Ecommerce.e_commerce.service;

import com.Ecommerce.e_commerce.DTO.ErrorDTO;
import com.Ecommerce.e_commerce.DTO.ProductDTO;
import com.Ecommerce.e_commerce.model.Category;
import com.Ecommerce.e_commerce.model.Product;
import com.Ecommerce.e_commerce.repo.CartRepository;
import com.Ecommerce.e_commerce.repo.CategoryRepository;
import com.Ecommerce.e_commerce.repo.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    @Autowired
    ProductRepository productRepository;

    @Autowired
    CategoryRepository categoryRepository;

    public ResponseEntity<Object> addProduct(ProductDTO productDTO) {

        Category category = categoryRepository.findByName(productDTO.getCategory());
        if(category == null){
            return ResponseEntity.internalServerError().body(new ErrorDTO("Category Not Found", 500));
        }

        Product product = new Product();
        product.setName(productDTO.getName());
        product.setDescription(productDTO.getDescription());
        product.setPrice(productDTO.getPrice());
        product.setStockQuantity(productDTO.getStockQuantity());
        product.setCategory(category);
        product.setImageUrl(productDTO.getImageUrl());
        return ResponseEntity.ok(productRepository.save(product));
    }

    public List<Product> getAllProduct() {
        return productRepository.findAll();
    }
}
