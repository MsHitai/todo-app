package com.example.todoapplication.controllers;

import com.example.todoapplication.interfaces.Organizable;
import com.example.todoapplication.model.Task;
import com.example.todoapplication.service.TaskOrganizer;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@Slf4j
public class TaskController {

    private final Organizable taskOrganizer = new TaskOrganizer();

    @GetMapping("/tasks")
    public Iterable<Task> findAll() {
        return taskOrganizer.getTasks();
    }

    @PostMapping("/task")
    public Task addTask(@Valid @RequestBody Task task) {
        log.debug("Получен запрос POST на создание задачи " + task.toString());
        taskOrganizer.addTask(task);
        return task;
    }

    @PutMapping("/task/{id}")
    public Task updateTask(@PathVariable int id, @Valid @RequestBody Task task){
        log.debug("Получен запрос PUT на обновление задачи " + task.toString());
        if(taskOrganizer.getTask(id) == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        taskOrganizer.updateTask(task);
        return task;
    }

    @DeleteMapping("/task/{id}")
    public void deleteTask(@PathVariable int id){
        log.debug("Получен запрос DELETE на удаление задачи " + taskOrganizer.getTask(id));
        if(taskOrganizer.getTask(id) == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        taskOrganizer.removeTask(id);
    }
}
