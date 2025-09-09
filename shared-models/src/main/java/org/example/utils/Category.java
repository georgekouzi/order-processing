package org.example.utils;

import java.util.HashMap;
import java.util.Map;

public enum Category {
    STANDARD("standard"),// Order can be fulfilled if available quantity >= requested amount
    PERISHABLE("perishable"),// Product must not be expired (simulate current date) AND have sufficien
    DIGITAL("digital"),// Always considered available
    UNKNOWN("unknown");//product ID or category -> reject the order


    private final String categoryName;
    private static final Map<String, Category> categoryMap = new HashMap<>();

    static {
        for (Category c : values()) {
            categoryMap.put(c.categoryName.toLowerCase(), c);
        }
    }

    Category(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public static Category fromString(String value) {
        if (value == null) {
            return UNKNOWN;
        }
        return categoryMap.getOrDefault(value.toLowerCase(), UNKNOWN);
    }
}



