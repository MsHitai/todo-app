package src.service;

import src.model.Task;

import java.time.LocalDate;

public interface Organizable {

    void createTask(Task task);

    void assignDeadLine(int id, LocalDate dueDate);

    void markAsDone(int id);

    void removeTask(int id);

    void changeTask(int id, String description);
}
