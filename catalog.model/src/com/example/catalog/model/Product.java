package com.example.catalog.model;

import java.math.BigDecimal;
import java.util.Objects;

public record Product(String id, String name, BigDecimal unitPrice) {
    public Product {
        Objects.requireNonNull(id, "id");
        Objects.requireNonNull(name, "name");
        Objects.requireNonNull(unitPrice, "unitPrice");
        if (id.isBlank() || name.isBlank() || unitPrice.signum() < 0) {
            throw new IllegalArgumentException("Invalid product");
        }
    }
}
