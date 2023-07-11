package com.example.todoapplication.repository;

import com.example.todoapplication.model.Task;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface TaskRepository extends CrudRepository<Task, Integer> {
    Optional<Task> findByDescription(String url);
}
