package org.example.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.example.models.Order;
import org.example.repositories.OrderRepository;
import org.example.services.OrderProducer;
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
    public String createOrder(@RequestBody Order order) throws JsonProcessingException {
        orderRepository.save(order);
        orderProducer.sendOrder(order);
        log.info(" innnnnnn ");

        return "create order!!!!!!!!";

    }
}
