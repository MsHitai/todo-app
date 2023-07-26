package com.example.todoapplication.service.impl;

import com.example.todoapplication.exceptions.DataNotFoundException;
import com.example.todoapplication.model.Role;
import com.example.todoapplication.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoleService {
    private final RoleRepository roleRepository;

    public Role getUserRole() {
        Role role = roleRepository.findByName("ROLE_USER");

        if (role == null) {
            throw new DataNotFoundException("Нет такой роли");
        }
        return role;
    }
}
