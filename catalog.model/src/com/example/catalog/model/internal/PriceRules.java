package com.example.catalog.model.internal;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

public final class PriceRules {
    private PriceRules() { }

    public static BigDecimal applyDiscount(BigDecimal price, int percent) {
        Objects.requireNonNull(price, "price");
        if (price.signum() < 0 || percent < 0 || percent > 100) {
            throw new IllegalArgumentException("Invalid price or discount");
        }
        return price.multiply(BigDecimal.valueOf(100L - percent))
                .divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP);
    }
}
