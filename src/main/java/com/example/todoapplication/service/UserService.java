package com.example.todoapplication.service;

import com.example.todoapplication.dto.RegistrationDto;
import com.example.todoapplication.dto.UserDto;
import com.example.todoapplication.model.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;
import java.util.Optional;

public interface UserService {

    UserDto findById(long userId);

    List<UserDto> findAllUsers();

    User findByEmail(String email);

    Optional<User> findByUsername(String username);

    User createNewUser(RegistrationDto registrationUserDto);

    UserDetails loadUserByUsername(String username);
}
