package org.example.repositories;

import org.example.models.Order;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class OrderRepository {
    private final RedisTemplate<String, Order> redisTemplate;

    public OrderRepository(RedisTemplate<String, Order> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public void save(Order order) {
        redisTemplate.opsForValue().set(order.getId(), order);
    }

    public Order findById(String orderId) {
        return redisTemplate.opsForValue().get(orderId);
    }
}
