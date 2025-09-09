package org.example.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.example.utils.OrderStatus;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

public class Order {
    public static final String topic = "orders";

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'", timezone = "UTC")
    private Instant requestedAt;
    private String customerName;
    private List<Item> items;
    private String id;
    private String status;

    public Order() {

    }

    public static void main(String[] args) {
        System.out.println(Instant.now());
    }

    public Order(String id, String customerName, String status, Instant requestedAt, List<Item> items) {
        this.id = validateId(id);
        this.customerName = customerName;
        this.requestedAt = validateRequestedAt(requestedAt);
        this.items = items;
        this.status = validateStatus(status);
    }

    private String validateStatus(String status) {
        if (status == null || status.isEmpty()) {
            status = OrderStatus.PENDING.getStatusName();
        }
        return status;
    }

    private Instant validateRequestedAt(Instant requestedAt) {
        if (requestedAt == null) {
            requestedAt = Instant.now();
        }
        return requestedAt;
    }


    private String validateId(String id) {
        if (id == null || id.isEmpty()) {
            id = UUID.randomUUID().toString();
        }
        return id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = validateId(id);
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = validateStatus(status);
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public Instant getRequestedAt() {
        return requestedAt;
    }

    public void setRequestedAt(Instant requestedAt) {
        this.requestedAt = requestedAt;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id='" + id + '\'' +
                ", status='" + status + '\'' +
                ", customerName='" + customerName + '\'' +
                ", requestedAt=" + requestedAt +
                ", items=" + items +
                '}';
    }
}
