package src.service;

import src.model.Task;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class TaskOrganizer implements Organizable{

    private int id;

    private Map<Integer, Task> tasks = new HashMap<>();

    private int createId() {
        return ++id;
    }


    @Override
    public void createTask(Task task) {
        task.setId(createId());
        tasks.put(task.getId(), task);
    }

    @Override
    public void assignDeadLine(int id, LocalDate dueDate) {

    }

    @Override
    public void markAsDone(int id) {

    }

    @Override
    public void removeTask(int id) {

    }

    @Override
    public void changeTask(int id, String description) {

    }
}
