package org.example.Component;

import org.example.models.ProductInfo;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Map;

@Component
public class ProductCatalog {

    private static final Map<String, ProductInfo> catalog = Map.of(
            "P1001", new ProductInfo("standard", 10, null),
            "P1002", new ProductInfo("perishable", 3, LocalDate.of(2025, 7, 1)), // תוקף בסדר
            "P1003", new ProductInfo("digital", 0, null),
            "P1004", new ProductInfo("standard", 0, null),
            "P1005", new ProductInfo("perishable", 2, LocalDate.of(2023, 12, 31)), // פג תוקף
            "P1006", new ProductInfo("standard", 5, null),
            "P1007", new ProductInfo("digital", 0, null),
            "P9999", new ProductInfo("unknown", 0, null)
    );


    public static ProductInfo getProduct(String productId) {
        return catalog.get(productId);
    }
}
