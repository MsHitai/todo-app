package com.example.todoapplication.service;

import com.example.todoapplication.user.User;

import java.util.HashMap;
import java.util.Map;

public class UserManager extends FileTaskOrganizer{

    private final String path;

    private final Map<String, User> users = new HashMap<>();

    public UserManager(String path) {
        super(null);
        this.path = path;
    }





}
