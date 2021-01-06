package com.example.user;

import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.function.Consumer;

@RestController
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    public List<UserInfo> getUsers() {
        return this.userService.getUsers();
    }

    @PostMapping("/users")
    public UserInfo createUsers(@RequestBody UserInfo userInfo) {
        return this.userService.createUser(userInfo);
    }
}
