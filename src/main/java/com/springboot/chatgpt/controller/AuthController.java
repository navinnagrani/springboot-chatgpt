package com.springboot.chatgpt.controller;

import com.springboot.chatgpt.dto.User;
import com.springboot.chatgpt.repository.UserRepository;
import com.springboot.chatgpt.util.JWTUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:4200")
@Slf4j
public class AuthController {

    private final UserRepository userRepository;
    private final JWTUtil jwtUtil;

    public AuthController(UserRepository userRepository, JWTUtil jwtUtil) {
        this.userRepository = userRepository;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/signup")
    public ResponseEntity<Map<String, String>> signup(@RequestBody User user) {
        log.info("Signup request received for email : {}",user.getEmail());
        userRepository.save(user);
        log.info("User registered successfully with email: {}", user.getEmail());
        return ResponseEntity.ok(
                Map.of("status", "SUCCESS", "message", "Signup successful")
        );
    }

    /*@PostMapping("/login")
    public String login(@RequestBody User user) {

        log.info("Login attempt for email: {}", user.getEmail());

        return userRepository.findByEmail(user.getEmail())
                .filter(u -> u.getPassword().equals(user.getPassword()))
                .map(u -> {
                    log.info("Login successful for email: {}", user.getEmail());
                    return "LOGIN_SUCCESS";
                })
                .orElseThrow(() -> {
                    log.warn("Invalid login attempt for email: {}", user.getEmail());
                    return new RuntimeException("Invalid credentials");
                });
    }*/


    @PostMapping("/login")
    public ResponseEntity<Map<String,String>> login(@RequestBody User user) {
        log.info("Login attempt for email: {}", user.getEmail());
        User dbUser = userRepository.findByEmail(user.getEmail())
                .filter(u -> u.getPassword().equals(user.getPassword()))
                .orElseThrow(() -> new RuntimeException("Invalid Credentials"));

        String token = jwtUtil.generateToken(dbUser.getEmail(),"USER");

        return ResponseEntity.ok(
                Map.of("token", token)
        );

    }
}
