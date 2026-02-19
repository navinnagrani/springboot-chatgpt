package com.springboot.chatgpt.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CartItem {
    private Product product;
    private int quantity;

    public double getPrice() {
        return product.getPrice();
    }

}
