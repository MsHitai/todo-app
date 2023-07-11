package com.example.todoapplication.service;

import com.example.todoapplication.dto.RegistrationDto;
import com.example.todoapplication.dto.UserDto;
import com.example.todoapplication.model.User;

import java.util.List;

public interface UserService {

    List<UserDto> findAllUsers();

    User findByEmail(String email);

    User findByUsername(String username);

    void saveUser(RegistrationDto user);
}
