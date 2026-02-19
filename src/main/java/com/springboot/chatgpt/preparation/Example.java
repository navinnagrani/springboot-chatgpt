package com.springboot.chatgpt.preparation;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class Example {

    public static void main(String[] args) {
        String text = "I celebrate myself, and sing myself," +
                "And what I assume you shall assume," +
                "For every atom belonging to me as good belongs to you." +
                "I loafe and invite my soul," +
                "I lean and loafe at my ease, observing a spear of summer grass." +
                "Houses and rooms are full of perfumes, the shelves are crowded with perfumes," +
                "I breathe the fragrance myself and know it and like it," +
                "The distillation would intoxicate me also, but I shall not let it." +
                "The atmosphere is not a perfume; it has no taste of the distillation, it is odourless," +
                "It is for my mouth forever; I am in love with it," +
                "I will go to the bank by the wood and become undisguised and naked…";
        //text = text.toLowerCase().replaceAll("[^a-z]","");
        String[] words = text.split("\s+");
        Map<String, Integer> wordCount = new HashMap<>();

        for(String word : words) {
            wordCount.put(word,wordCount.getOrDefault(word,0)+1);
        }

        System.out.println("Duplicate words and their count");
        for(Map.Entry<String, Integer> entry : wordCount.entrySet()) {
            if(entry.getValue() > 1) {
                System.out.println(entry.getKey() +" "+entry.getValue());
            }
        }
    }
}
