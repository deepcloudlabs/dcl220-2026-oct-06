package com.example;

public class CancelledState implements OrderState {

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
                "Cancelled order cannot be modified."
        );
    }

    @Override
    public String name() {
        return "CANCELLED";
    }
}