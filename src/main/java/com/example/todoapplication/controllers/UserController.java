package com.example.todoapplication.controllers;

import com.example.todoapplication.dto.UserDto;
import com.example.todoapplication.service.impl.UserDetailsServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {

    private final UserDetailsServiceImpl userService;

    @Autowired
    public UserController(UserDetailsServiceImpl userService) {
        this.userService = userService;
    }

    @GetMapping("/{userId}")
    public UserDto findById(@PathVariable() Long userId) {
        log.debug("Поступил запрос GET на пользователя по id {}", userId);
        return userService.findById(userId);
    }
}
