package com.example.todoapplication.repository;

import com.example.todoapplication.model.Task;
import org.springframework.data.repository.CrudRepository;

public interface TaskRepository extends CrudRepository<Task, Integer> {
}
