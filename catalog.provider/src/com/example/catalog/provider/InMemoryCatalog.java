package com.example.catalog.provider;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import com.example.catalog.api.CatalogService;
import com.example.catalog.model.Product;
import com.example.catalog.model.internal.PriceRules;

public final class InMemoryCatalog implements CatalogService {
    private final List<Product> products = List.of(
            new Product("P-100", "Cotton T-shirt", new BigDecimal("25.00")),
            new Product("P-200", "Recycled jacket", new BigDecimal("80.00")));

    public InMemoryCatalog() { }

    @Override
    public String providerName() {
        return "In-memory catalog";
    }

    @Override
    public List<Product> products() {
        return products;
    }

    @Override
    public Optional<Product> findById(String id) {
        Objects.requireNonNull(id, "id");
        return products.stream().filter(p -> p.id().equals(id)).findFirst();
    }

    @Override
    public BigDecimal discountedPrice(Product product, int discountPercent) {
        Objects.requireNonNull(product, "product");
        // Legal because the model exports this helper specifically to us.
        return PriceRules.applyDiscount(product.unitPrice(), discountPercent);
    }

}
