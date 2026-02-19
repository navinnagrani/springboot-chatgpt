package com.springboot.chatgpt.dto;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name="ORDERS")
public class OrderEntity {


    @Id
    @GeneratedValue
    private Long id;

    private String email;
    private double totalAmount;
    private String status;
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderItem> items;

    @PrePersist
    public void onCreate() {
        this.createdAt = LocalDateTime.now();
        status = "PLACED";
    }
}
