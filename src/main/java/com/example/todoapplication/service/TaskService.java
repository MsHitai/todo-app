package com.example.todoapplication.service;

import com.example.todoapplication.model.Task;

import java.util.List;

public interface TaskService {


    List<Task> getTasks();

    Task addTask(Task task);

    Task findById(int id);

    Task updateTask(Task task);

    void removeTask(int id);

}
