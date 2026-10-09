package com.example;

public class DeliveredState implements OrderState {

    @Override
    public void pay(Order order) {
        invalid();
    }

    @Override
    public void ship(Order order) {
        invalid();
    }

    @Override
    public void deliver(Order order) {
        invalid();
    }

    @Override
    public void cancel(Order order) {
        invalid();
    }

    private void invalid() {
        throw new IllegalStateException(
                "No operation is allowed after delivery."
        );
    }

    @Override
    public String name() {
        return "DELIVERED";
    }
}