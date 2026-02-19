package com.springboot.chatgpt.controller;

import com.springboot.chatgpt.dto.Product;
import com.springboot.chatgpt.repository.ProductRepository;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@CrossOrigin(origins = "http://localhost:4200")
public class ProductController {
    private final ProductRepository productRepository;

    public ProductController(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @GetMapping
    public List<Product> getProducts() {
        if ((productRepository.count() == 0)) {
            productRepository.saveAll(List.of(
                    new Product("Product A",500),
                    new Product("Product B",300),
                    new Product("Product C",700)
            ));
        }
        return productRepository.findAll();
    }
}
