package src.service;

import src.model.Task;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public interface Organizable {

    void addTask(Task task);

    Task createTask(Scanner scanner);

    List<Task> getTasks();

    void assignDeadLine(int id, LocalDate dueDate);

    void markAsDone(int id);

    void removeTask(int id);

    void changeTask(int id, String description);


}
