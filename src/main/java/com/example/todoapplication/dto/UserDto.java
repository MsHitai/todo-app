package com.example.todoapplication.dto;

import com.example.todoapplication.model.Role;
import com.example.todoapplication.model.Task;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.Set;

@Data
@Builder
public class UserDto {

    private long id;

    @NotNull
    private String username;

    @Email
    @NotNull
    private String email;

    private Set<Task> tasks;

    private List<Role> roles;
}
