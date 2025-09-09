package org.example.services;

import lombok.extern.slf4j.Slf4j;
import org.example.models.InventoryCheckResultEvent;
import org.example.models.Order;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import static org.example.utils.OrderStatus.APPROVED;

@Slf4j
@Service
public class NotificationConsumer {

    private final RedisTemplate<String, Order> redisTemplate;

    public NotificationConsumer(RedisTemplate<String, Order> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @KafkaListener(topics = "inventory-results", groupId = "notification-group")
    public void consumeResult(InventoryCheckResultEvent event) {
        log.info("Received InventoryCheckResultEvent obj: {}", event);

        String key = event.getOrderId();
        Order order = redisTemplate.opsForValue().get(key);

        if (order == null) {
            log.info("Order : {} not found in Redis", event.getOrderId());
        } else if (event.getOrderStatus().equalsIgnoreCase(APPROVED.getStatusName())) {
            log.info("Order - {} for customer {} is approved!!!", order.getId(), order.getCustomerName());
        } else {
            log.info("Order {} rejected. Missing items: {}", order.getId(), event.getMissingItems());
        }
    }
}
