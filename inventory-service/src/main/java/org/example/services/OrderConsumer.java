package org.example.services;

import lombok.extern.slf4j.Slf4j;
import org.example.Component.ProductCatalog;
import org.example.models.InventoryCheckResultEvent;
import org.example.models.Item;
import org.example.models.Order;
import org.example.models.ProductInfo;
import org.example.utils.Category;
import org.example.utils.OrderStatus;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.example.utils.Category.*;

@Slf4j
@Service
public class OrderConsumer {

    private final KafkaTemplate<String, InventoryCheckResultEvent> kafkaTemplate;

    public OrderConsumer(KafkaTemplate<String, InventoryCheckResultEvent> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @KafkaListener(topics = "orders", groupId = "inventory-group")
    public void consumeOrder(Order order) {

        log.info("innnnnnn Received order ={} ", order);

        List<String> missingItems = validateOrder(order);
        String orderStatus = missingItems.isEmpty() ? OrderStatus.APPROVED.getStatusName() : OrderStatus.REJECTED.getStatusName();

        InventoryCheckResultEvent result = new InventoryCheckResultEvent(
                order.getId(),
                orderStatus,
                missingItems
        );
        log.info("Sending result = {} ", result);

        kafkaTemplate.send("inventory-results", result);
    }

    private List<String> validateOrder(Order order) {
        List<String> missingItems = new ArrayList<>();

        for (Item item : order.getItems()) {
            String productId = item.getProductId();
            ProductInfo product = ProductCatalog.getProduct(productId);
            if (product == null) {
                missingItems.add(productId + " " + UNKNOWN.getCategoryName());
                continue;
            }
            Category category = Category.fromString(product.getCategory());
            switch (category) {
                case STANDARD -> checkForStandardMissingItems(item, product, missingItems);
                case PERISHABLE -> checkForPerishableMissingItems(item, product, missingItems);
                case DIGITAL -> {
                    //Always considered available
                }
                default -> missingItems.add(productId + " invalid category");
            }
        }
        return missingItems;
    }

    private void checkForStandardMissingItems(Item item, ProductInfo product, List<String> missingItems) {
        if (product.getAvailableQuantity() < item.getQuantity()) {
            missingItems.add(item.getProductId() + " insufficient stock");
        }

    }

    private void checkForPerishableMissingItems(Item item, ProductInfo product, List<String> missingItems) {
        if (product.getExpirationDate() != null &&
                product.getExpirationDate().isBefore(LocalDate.now())) {
            missingItems.add(item.getProductId() + " expired");
        } else if (product.getAvailableQuantity() < item.getQuantity()) {
            missingItems.add(item.getProductId() + " insufficient stock");
        }

    }
}
