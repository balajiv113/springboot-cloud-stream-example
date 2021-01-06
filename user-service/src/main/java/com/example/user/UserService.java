package com.example.user;

import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

@Service
public class UserService {
    private final StreamBridge streamBridge;
    private final Map<String, UserInfo> users = new HashMap<>();

    public UserService(StreamBridge streamBridge) {
        this.streamBridge = streamBridge;
    }

    public List<UserInfo> getUsers() {
        return new ArrayList<>(users.values());
    }

    public UserInfo createUser(UserInfo userInfo) {
        return users.put(userInfo.getUserName(), userInfo);
    }

    @Bean
    Consumer<Order> consumeOrder() {
        return order -> {
            UserInfo userInfo = users.get(order.getUserName());
            if (userInfo != null && userInfo.getCredits() >= order.getOrderValue()) {
                userInfo.setCredits(userInfo.getCredits() - order.getOrderValue());
                streamBridge.send("approved-out-0", order);
            } else {
                streamBridge.send("rejected-out-0", order);
            }
        };
    }
}
