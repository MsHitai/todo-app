package com.example.todoapplication.service.impl;

import com.example.todoapplication.dto.RegistrationDto;
import com.example.todoapplication.dto.UserDto;
import com.example.todoapplication.exceptions.DataNotFoundException;
import com.example.todoapplication.mapper.UserMapper;
import com.example.todoapplication.model.User;
import com.example.todoapplication.repository.RoleRepository;
import com.example.todoapplication.repository.UserRepository;
import com.example.todoapplication.service.UserService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserDetailsService, UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    private final RoleRepository roleRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder,
                           RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(
                String.format("Пользователь '%s' не найден", username)
        ));
        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                user.getRoles().stream()
                        .map(role -> new SimpleGrantedAuthority(role.getName()))
                        .collect(Collectors.toList())
        );
    }
    @Override
    public UserDto findById(long userId) {
        User user = userRepository.findById(userId).orElseThrow(() ->
                new DataNotFoundException("Пользователя с id " + userId + " нет в базе данных"));
        return UserMapper.mapToUserDto(user);
    }

    @Override
    public List<UserDto> findAllUsers() {
        Collection<User> users = (Collection<User>) userRepository.findAll();
        return users.stream().map(UserMapper::mapToUserDto).collect(Collectors.toList());
    }

    @Override
    public User findByEmail(String email) {
        return null;
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return Optional.ofNullable(userRepository.findByUsername(username));
    }

    @Override
    public User createNewUser(RegistrationDto registrationUserDto) {
        User user = new User();
        user.setUsername(registrationUserDto.getUsername());
        user.setEmail(registrationUserDto.getEmail());
        user.setPassword(passwordEncoder.encode(registrationUserDto.getPassword()));
        user.setRoles(List.of(roleRepository.findByName("ROLE_USER")));
        return userRepository.save(user);
    }
}
