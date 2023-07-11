package com.example.todoapplication.service.impl;

import com.example.todoapplication.dto.UserDto;
import com.example.todoapplication.mapper.UserMapper;
import com.example.todoapplication.model.User;
import com.example.todoapplication.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    @Autowired
    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if (user != null) {
            UserDto userDto = UserMapper.mapToUserDto(user);
            User authUser = UserMapper.mapToUser(userDto, user.getPassword());

            return (UserDetails) authUser;
        } else {
            throw new UsernameNotFoundException("Invalid username or password");
        }
    }
}
