package com.springboot.chatgpt.dto;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "ORDER_ITEMS")
public class OrderItem {

    @Id
    @GeneratedValue
    private Long id;

    private String productName;
    private double price;
    private int quantity;

    @ManyToOne
    @JoinColumn(name="order_id")
    private OrderEntity order;
}
