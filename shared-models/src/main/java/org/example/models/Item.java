package org.example.models;

public class Item {
    private String productId;
    private int quantity;
    private String category;

    public Item(String productId, int quantity, String category) {
        this.productId = productId;
        this.quantity = quantity;
        this.category = category;
    }

    public Item() {

    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void updateQuantity() {
        quantity++;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
