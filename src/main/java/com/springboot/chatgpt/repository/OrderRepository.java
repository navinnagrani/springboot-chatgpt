package com.springboot.chatgpt.repository;

import com.springboot.chatgpt.dto.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<OrderEntity,Long> {

    List<OrderEntity> findByEmailOrderByCreatedAtDesc(String email);
}
