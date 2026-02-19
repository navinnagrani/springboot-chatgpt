package com.springboot.chatgpt.preparation;
class Animal {
    void sound() {
        System.out.println("Animal Sound");
    }
}
class Dog extends Animal {
    @Override
    void sound() {
        System.out.println("Dog Sound");
    }
}

public class PolymorphismDemo {
    public static void main(String[] args) {
        Animal a = new Dog();
        a.sound();
    }

}
