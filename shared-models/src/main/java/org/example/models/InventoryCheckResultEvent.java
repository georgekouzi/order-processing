package org.example.models;

import java.util.List;

public class InventoryCheckResultEvent {
    private String orderId;
    private boolean approved;
    private List<String> missingItems;

    public InventoryCheckResultEvent() {
        
    }

    public InventoryCheckResultEvent(String orderId, boolean approved, List<String> missingItems) {
        this.orderId = orderId;
        this.approved = approved;
        this.missingItems = missingItems;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public boolean isApproved() {
        return approved;
    }

    public void setApproved(boolean approved) {
        this.approved = approved;
    }

    public List<String> getMissingItems() {
        return missingItems;
    }

    public void setMissingItems(List<String> missingItems) {
        this.missingItems = missingItems;
    }
}
