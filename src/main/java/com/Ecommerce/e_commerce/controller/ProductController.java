package com.Ecommerce.e_commerce.controller;

import com.Ecommerce.e_commerce.DTO.ProductDTO;
import com.Ecommerce.e_commerce.model.Product;
import com.Ecommerce.e_commerce.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {
    @Autowired
    ProductService productService;

    @PostMapping("/add")
    public ResponseEntity<Object> addProduct(@RequestBody ProductDTO productDTO){
        return productService.addProduct(productDTO);
    }

    @GetMapping("/")
    public List<Product> getAllProduct(){
        return productService.getAllProduct();
    }
}
