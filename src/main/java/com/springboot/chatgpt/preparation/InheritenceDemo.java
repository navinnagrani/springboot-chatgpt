package com.springboot.chatgpt.preparation;
class Vehicle {
    public void start() {
        System.out.println("Vehicle Starts");
    }
}
class Car extends Vehicle {
    public void drive() {
        System.out.println("Car drives");
    }
}

public class InheritenceDemo {
    public static void main(String[] args) {
        Car c = new Car();
        c.start();
        c.drive();
    }
}
