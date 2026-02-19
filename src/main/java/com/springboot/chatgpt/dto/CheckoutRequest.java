package com.springboot.chatgpt.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CheckoutRequest {

    private String email;
    private List<CartItem> cart;

}
