package com.springboot.chatgpt.preparation;


interface Payment {
    void pay();
}

class MyPayment implements Payment {
    public void pay() {
        System.out.println("I made payment");
    }
}

abstract class Bank {
    abstract void interestRate();

    void branch() {
        System.out.println("Bank branch available");
    }
}
class SBI extends Bank {

    void interestRate() {
        System.out.println("SBI interest rate is 6%");
    }
}

public class AbstractionDemo {
    public static void main(String[] args) {
        Bank b = new SBI();
        b.branch();
        b.interestRate();

        //Using Interface
        MyPayment mp = new MyPayment();
        mp.pay();
    }
}
