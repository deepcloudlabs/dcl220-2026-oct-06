package com.example.cdi.decorator;

import jakarta.annotation.Priority;
import jakarta.decorator.Decorator;
import jakarta.decorator.Delegate;
import jakarta.inject.Inject;
import jakarta.interceptor.Interceptor;

import java.util.Locale;

@Decorator
@Priority(Interceptor.Priority.APPLICATION)
public abstract class UppercaseMessageDecorator implements MessageService {

    @Inject
    @Delegate
    private MessageService delegate;

    @Override
    public String createMessage(String name) {
        return delegate.createMessage(name).toUpperCase(Locale.ROOT);
    }
}
