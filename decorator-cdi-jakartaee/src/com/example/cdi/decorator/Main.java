package com.example.cdi.decorator;

import jakarta.enterprise.inject.se.SeContainer;
import jakarta.enterprise.inject.se.SeContainerInitializer;

public final class Main {

    private Main() {
    }

    public static void main(String[] args) {
        String name = args.length == 0 ? "Jakarta CDI" : args[0];

        try (SeContainer container = SeContainerInitializer.newInstance().initialize()) {
            MessageService service = container.select(MessageService.class).get();
            System.out.println(service.createMessage(name));
        }
    }
}
