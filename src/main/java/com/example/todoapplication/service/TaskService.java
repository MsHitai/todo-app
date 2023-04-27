package com.example.todoapplication.service;

import com.example.todoapplication.model.Task;
import com.example.todoapplication.repository.TaskRepository;
import org.springframework.stereotype.Service;

@Service
public class TaskService {
    private final TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public void addTask(Task task) {
        taskRepository.save(task);
    }

    public Iterable<Task> getTasks() {
        return taskRepository.findAll();
    }

    public Task getTask(int id) {
        return taskRepository.findById(id).orElse(null);
    }

    public void removeTask(int id) {
        taskRepository.deleteById(id);
    }

    public void updateTask(Task task) {
        taskRepository.deleteById(task.getId());
        taskRepository.save(task);
    }
}
