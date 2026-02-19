package com.springboot.chatgpt.controller;

import com.springboot.chatgpt.dto.OrderEntity;
import com.springboot.chatgpt.dto.OrderItem;
import com.springboot.chatgpt.repository.OrderRepository;
import com.springboot.chatgpt.util.JWTUtil;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@CrossOrigin(origins = "http://localhost:4200")
public class OrderController {

    private final OrderRepository orderRepository;
    private final JWTUtil jwtUtil;

    public OrderController(OrderRepository orderRepository, JWTUtil jwtUtil) {
        this.orderRepository = orderRepository;
        this.jwtUtil = jwtUtil;
    }

    /*@GetMapping("/my-orders")
    public List<OrderEntity> getMyOrders(@RequestParam String email) {
        return orderRepository.findByEmailOrderByCreatedAtDesc(email);
    }*/

    @GetMapping("/my-orders")
    public List<OrderEntity> getOrders(@RequestHeader("Authorization") String authHeader) {

        String email = jwtUtil.extractEmail(authHeader.replace("Bearer ", ""));
        return orderRepository.findByEmailOrderByCreatedAtDesc(email);
    }

    // ❌ CANCEL ORDER
    @PutMapping("/{id}/cancel")
    public void cancel(@PathVariable Long id) {
        OrderEntity order = orderRepository.findById(id).orElseThrow();
        order.setStatus("CANCELLED");
        orderRepository.save(order);
    }

    // 🔁 REORDER
    @PostMapping("/{id}/reorder")
    public void reorder(@PathVariable Long id) {

        OrderEntity old = orderRepository.findById(id).orElseThrow();

        OrderEntity copy = new OrderEntity();
        copy.setEmail(old.getEmail());
        copy.setTotalAmount(old.getTotalAmount());

        List<OrderItem> items = old.getItems().stream().map(i -> {
            OrderItem ni = new OrderItem();
            ni.setProductName(i.getProductName());
            ni.setPrice(i.getPrice());
            ni.setQuantity(i.getQuantity());
            ni.setOrder(copy);
            return ni;
        }).toList();

        copy.setItems(items);
        orderRepository.save(copy);
    }

    // 👑 ADMIN – ALL ORDERS
    @GetMapping("/admin/all")
    public List<OrderEntity> allOrders(
            @RequestHeader("Authorization") String auth) {

        String role = jwtUtil.extractRole(auth.replace("Bearer ", ""));
        if (!"ADMIN".equals(role)) {
            throw new RuntimeException("Access denied");
        }
        return orderRepository.findAll();
    }
}
