package com.springboot.chatgpt.controller;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Example {
    public static void main(String[] args) {
        List<String> fruits= Arrays.asList("apple","apple","banana","banana","mango");
        Map<String,Long> countMap = fruits.stream().collect(Collectors.groupingBy(Function.identity(),Collectors.counting()));
        System.out.println(countMap);

    }
}
