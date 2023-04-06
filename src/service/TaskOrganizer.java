package service;

import interfaces.Organizable;
import model.Task;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;

public class TaskOrganizer implements Organizable {

    private int id;

    protected final Map<Integer, Task> tasks = new HashMap<>();

    private int createId() {
        return ++id;
    }


    @Override
    public void addTask(Task task) {
        if (task.getId() > id) {
            id = task.getId();
        }
        tasks.put(task.getId(), task);
    }
    @Override
    public Task createTask(String description, String date) {
        Task task = new Task();
        task.setId(createId());
        task.setDescription(description);
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        try {
            task.setDueDate(LocalDate.parse(date, dtf));
        } catch (DateTimeParseException e) {
            System.out.println(e.getMessage());
        }
        task.setDone(false);
        return task;
    }
    @Override
    public List<Task> getTasks() {
        List<Task> tasksToReturn = new ArrayList<>();
        for (Integer id : tasks.keySet()) {
            tasksToReturn.add(tasks.get(id));
        }
        return tasksToReturn;
    }

    @Override
    public Task getTask(int id) {
        return tasks.get(id);
    }

    @Override
    public void assignDeadLine(int id, String dueDate) {
        if (tasks.containsKey(id)) {
            Task task = tasks.get(id);
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            try {
                task.setDueDate(LocalDate.parse(dueDate, dtf));
            } catch (DateTimeParseException e) {
                System.out.println(e.getMessage());
            }
        } else {
            System.out.println("Под таким номером нет задачи.");
        }
    }

    @Override
    public void markAsDone(int id) {
        if (tasks.containsKey(id)) {
            Task task = tasks.get(id);
            task.setDone(true);
        } else {
            System.out.println("Под таким номером нет задачи.");
        }
    }

    @Override
    public void removeTask(int id) {
        if (tasks.containsKey(id)) {
            tasks.remove(id);
        } else {
            System.out.println("Под таким номером нет задачи.");
        }
    }

    @Override
    public void changeTask(int id, String description) {
        if (tasks.containsKey(id)) {
            Task task = tasks.get(id);
            task.setDescription(description);
        } else {
            System.out.println("Под таким номером нет задачи.");
        }
    }

    @Override
    public void updateTask(Task task) {
        int id = task.getId();
        if (tasks.containsKey(id)) {
            tasks.put(id, task);
        }
    }

    @Override
    public void deleteAllTasks() {
        tasks.clear();
    }

    @Override
    public void deleteAllDoneTasks() {
        if (tasks.isEmpty()) {
            return;
        }
        ArrayList<Task> doneTasks = new ArrayList<>();
        for (Task task : tasks.values()) {
            if (task.isDone()) {
                doneTasks.add(task);
            }
        }

        for (Task doneTask : doneTasks) {
            tasks.remove(doneTask.getId());
        }
    }
}
