package org.example.utils;

public enum OrderStatus {
    PENDING("PENDING", 1),
    APPROVED("APPROVED", 2),
    REJECTED("REJECTED", 3),

    ;
    private String statusName;
    private int statusCode;

    OrderStatus(String statusName, int statusCode) {
        this.statusName = statusName;
        this.statusCode = statusCode;
    }

    public String getStatusName() {
        return statusName;
    }

    public int getStatusCode() {
        return statusCode;
    }
}
