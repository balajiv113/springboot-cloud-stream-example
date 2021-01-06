package com.example.order;

import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.Consumer;

@Service
public class OrderService {
    private final Map<String, Order> orders = new HashMap<>();
    private final StreamBridge streamBridge;

    public OrderService(StreamBridge streamBridge) {
        this.streamBridge = streamBridge;
    }

    public List<Order> getOrders() {
        return new ArrayList<>(orders.values());
    }

    public Order createOrder(Order order) {
        order.setOrderId(UUID.randomUUID().toString());
        order.setOrderStatus(Order.Status.Processing);
        orders.put(order.getOrderId(), order);
        this.streamBridge.send("order-out-0", order);
        return order;
    }

    @Bean
    Consumer<Order> approvedOrder() {
        return s -> {
            Order order = orders.get(s.getOrderId());
            order.setOrderStatus(Order.Status.Accepted);
            System.out.println("Order with ID - "+ s.getOrderId() +" accepted");
        };
    }

    @Bean
    Consumer<Order> rejectedOrder() {
        return s -> {
            Order order = orders.get(s.getOrderId());
            order.setOrderStatus(Order.Status.Rejected);
            System.out.println("Order with ID - "+ s.getOrderId() +" rejected");
        };
    }
}
