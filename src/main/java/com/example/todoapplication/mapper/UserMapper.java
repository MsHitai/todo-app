package com.example.todoapplication.mapper;

import com.example.todoapplication.dto.UserDto;
import com.example.todoapplication.model.User;
import lombok.experimental.UtilityClass;

@UtilityClass
public class UserMapper {

    public UserDto mapToUserDto(User user) {
        return UserDto.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .tasks(user.getTasks())
                .roles(user.getRoles())
                .build();
    }

    public User mapToUser (UserDto userDto, String password) {
        return User.builder()
                .id(userDto.getId())
                .username(userDto.getUsername())
                .email(userDto.getEmail())
                .password(password)
                .tasks(userDto.getTasks())
                .roles(userDto.getRoles())
                .build();
    }
}
