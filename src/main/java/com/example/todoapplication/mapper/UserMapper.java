package com.example.todoapplication.mapper;

import com.example.todoapplication.dto.UserDto;
import com.example.todoapplication.model.Role;
import com.example.todoapplication.model.Task;
import com.example.todoapplication.model.User;
import lombok.experimental.UtilityClass;

import java.util.List;
import java.util.Set;

@UtilityClass
public class UserMapper {

    public UserDto mapToUserDto(User user) {
        return UserDto.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .build();
    }

    public User mapToUser(UserDto userDto, String password, Set<Task> tasks, List<Role> roles) {
        return User.builder()
                .id(userDto.getId())
                .username(userDto.getUsername())
                .email(userDto.getEmail())
                .password(password)
                .tasks(tasks)
                .roles(roles)
                .build();
    }
}
