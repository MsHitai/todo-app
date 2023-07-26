package com.example.todoapplication.controllers;

import com.example.todoapplication.model.Task;
import com.example.todoapplication.service.TaskService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@Slf4j
public class TaskController {

    private final TaskService taskService;

    @Autowired
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping("/tasks")
    public List<Task> findAll() {
        return taskService.getTasks();
    }

    @GetMapping("/tasks/{id}")
    public Task findById(@PathVariable(required = false) int id) {
        return taskService.findById(id);
    }

    @PostMapping("/task")
    public Task addTask(@Valid @RequestBody Task task) {
        log.debug("Получен запрос POST на создание задачи " + task.toString());
        return taskService.addTask(task);
    }

    @PutMapping("/task/{id}")
    public Task updateTask(@PathVariable int id, @Valid @RequestBody Task task) {
        log.debug("Получен запрос PUT на обновление задачи " + task.toString());
        if (taskService.findById(id) == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return taskService.updateTask(task);
    }

    @DeleteMapping("/task/{id}")
    public void deleteTask(@PathVariable int id) {
        log.debug("Получен запрос DELETE на удаление задачи " + taskService.findById(id));
        if (taskService.findById(id) == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        taskService.removeTask(id);
    }
}
