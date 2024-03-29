package com.example.todoapplication.interfaces;

import com.example.todoapplication.model.Task;

import java.util.List;

public interface Organizable {

    void addTask(Task task);

    Task createTask(String description, String date);

    List<Task> getTasks();

    Task getTask(int id);

    void updateTask(Task task);

    void assignDeadLine(int id, String dueDate);

    void markAsDone(int id);

    void removeTask(int id);

    void changeTask(int id, String description);

    void deleteAllTasks();

    void deleteAllDoneTasks();
}
