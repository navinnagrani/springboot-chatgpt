package com.springboot.chatgpt.controller;

import com.springboot.chatgpt.dto.CheckoutRequest;
import com.springboot.chatgpt.dto.OrderEntity;
import com.springboot.chatgpt.dto.OrderItem;
import com.springboot.chatgpt.repository.OrderRepository;
import com.springboot.chatgpt.util.JWTUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/cart")
@CrossOrigin(origins = "http://localhost:4200")
public class CartController {

    private final OrderRepository orderRepository;
    private final JWTUtil jwtUtil;

    public CartController(OrderRepository orderRepository, JWTUtil jwtUtil) {
        this.orderRepository = orderRepository;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/checkout")
    public ResponseEntity<Map<String, String>> checkout(
            @RequestBody CheckoutRequest request) {

        double total = request.getCart().stream()
                .mapToDouble(i -> i.getPrice() * i.getQuantity())
                .sum();

        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setEmail(request.getEmail()); // ✅ logged-in email
        orderEntity.setTotalAmount(total);

        orderRepository.save(orderEntity);

        return ResponseEntity.ok(
                Map.of(
                        "status", "SUCCESS",
                        "message", "Order placed successfully"
                )
        );
    }

    /*@PostMapping("/checkout")
    public ResponseEntity<?> checkout(
            @RequestBody CheckoutRequest request,
            @RequestHeader("Authorization") String authHeader) {

        String email = jwtUtil.extractEmail(authHeader.replace("Bearer ", ""));

        OrderEntity order = new OrderEntity();
        order.setEmail(email);

        List<OrderItem> items = request.getCart().stream().map(c -> {
            OrderItem item = new OrderItem();
            item.setProductName(c.getProduct().getName());
            item.setPrice(c.getProduct().getPrice());
            item.setQuantity(c.getQuantity());
            item.setOrder(order);
            return item;
        }).toList();

        order.setItems(items);
        order.setTotalAmount(
                items.stream().mapToDouble(i -> i.getPrice() * i.getQuantity()).sum()
        );

        orderRepository.save(order);
        return ResponseEntity.ok(Map.of("status", "SUCCESS"));
    }*/
}
