package com.example.cdi.decorator;

import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class PlainMessageService implements MessageService {

    @Override
    public String createMessage(String name) {
        return "Hello, " + name + "!";
    }
}
