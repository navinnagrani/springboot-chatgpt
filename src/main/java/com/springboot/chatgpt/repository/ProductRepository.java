package com.springboot.chatgpt.repository;

import com.springboot.chatgpt.dto.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
