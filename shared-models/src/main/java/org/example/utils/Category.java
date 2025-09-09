package org.example.utils;

public enum Category {
    UNKNOWN("unknown", 0),
    STANDARD("standard", 1),
    PERISHABLE("perishable", 2),
    DIGITAL("digital", 3),
    ;
    private String categoryName;
    private int categoryCode;

    Category(String categoryName, int categoryCode) {
        this.categoryName = categoryName;
        this.categoryCode = categoryCode;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public int getCategoryCode() {
        return categoryCode;
    }
}



