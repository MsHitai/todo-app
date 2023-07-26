package com.example.todoapplication.service;

import com.example.todoapplication.exceptions.DataNotFoundException;
import com.example.todoapplication.model.Task;
import com.example.todoapplication.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskServiceImpl implements TaskService {
    private final TaskRepository taskRepository;

    @Autowired
    public TaskServiceImpl(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }


    @Override
    public Task addTask(Task task) {
        return taskRepository.save(task);
    }

    @Override
    public List<Task> getTasks() {
        return (List<Task>) taskRepository.findAll();
    }

    @Override
    public Task findById(int id) {
        return taskRepository.findById(id).orElseThrow(() ->
                new DataNotFoundException("Задачи с id " + id + " нет в базе данных"));
    }

    @Override
    public Task updateTask(Task task) {
        return taskRepository.save(task);
    }

    @Override
    public void removeTask(int id) {
        taskRepository.deleteById(id);
    }

}
