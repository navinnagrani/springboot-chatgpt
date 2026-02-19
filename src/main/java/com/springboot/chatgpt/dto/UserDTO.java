package com.springboot.chatgpt.dto;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class UserDTO {
    private String name;
    private String age;

    public UserDTO(String name, String age) {
        this.name = name;
        this.age = age;
    }
}
