package org.example.models;

import java.util.List;

public class InventoryCheckResultEvent {
    private String orderId;
    private String orderStatus;
    private List<String> missingItems;

    public InventoryCheckResultEvent() {

    }

    public InventoryCheckResultEvent(String orderId, String orderStatus, List<String> missingItems) {
        this.orderId = orderId;
        this.orderStatus = orderStatus;
        this.missingItems = missingItems;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String OrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public List<String> getMissingItems() {
        return missingItems;
    }

    public void setMissingItems(List<String> missingItems) {
        this.missingItems = missingItems;
    }
}
