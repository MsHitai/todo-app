package com.example.todoapplication.repository;

import com.example.todoapplication.model.Task;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TaskRepository extends CrudRepository<Task, Integer> {
    Optional<Task> findByDescription(String description);

}
