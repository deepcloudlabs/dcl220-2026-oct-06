package com.example.catalog.api;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import com.example.catalog.model.Product;

public interface CatalogService {
    String providerName();

	List<Product> products();

    Optional<Product> findById(String id);

    BigDecimal discountedPrice(Product product, int discountPercent);
}
