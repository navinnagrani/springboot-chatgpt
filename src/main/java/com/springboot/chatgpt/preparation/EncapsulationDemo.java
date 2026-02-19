package com.springboot.chatgpt.preparation;

class Account {
    private double balance = 20;

    public void deposit(double amount) {
        if(amount > 0) {
            balance+=amount;
        }
    }
    public double getBalance() {
        return balance;
    }
}
public class EncapsulationDemo {
    public static void main(String[] args) {
        Account acc = new Account();
        acc.deposit(1000);
        System.out.print(acc.getBalance());
        Math.max(1,2);
    }
}
