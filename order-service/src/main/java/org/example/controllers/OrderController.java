package org.example.controllers;

import lombok.extern.slf4j.Slf4j;
import org.example.models.Order;
import org.example.repositories.OrderRepository;
import org.example.services.OrderProducer;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/orders")
@Slf4j
public class OrderController {
    private final OrderRepository orderRepository;
    private final OrderProducer orderProducer;

    public OrderController(OrderRepository orderRepository, OrderProducer orderProducer) {
        this.orderRepository = orderRepository;
        this.orderProducer = orderProducer;
    }

    @PostMapping
    public ResponseEntity<?> createOrder(@RequestBody Order order) {
        log.info("New order : {}", order);
        try {
            orderRepository.save(order);
            orderProducer.sendOrder(order);
            return ResponseEntity.ok("Order created!!!");
        } catch (Exception e) {
            log.error("Failed to process order {}: {}", order.getId(), e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to create order. Please try again later.");
        }
    }

}
