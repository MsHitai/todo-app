package src.service;

import src.model.Task;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class TaskOrganizer implements Organizable{

    private int id;

    private final Map<Integer, Task> tasks = new HashMap<>();

    private int createId() {
        return ++id;
    }


    @Override
    public void addTask(Task task) {
        tasks.put(task.getId(), task);
    }
    @Override
    public Task createTask(Scanner scanner) {
        Task task = new Task();
        task.setId(createId());
        task.setDescription(addDescription(scanner));
        task.setDueDate(addDate(scanner));
        task.setDone(false);
        return task;
    }

    private String addDescription(Scanner scanner) {
        System.out.println("Добавьте описание: ");
        scanner.nextLine();
        return scanner.nextLine();
    }

    private LocalDate addDate (Scanner scanner) {
        System.out.print("Введите дату [дд. мм. гггг]: ");
        String str = scanner.nextLine();
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd. MM. yyyy");
        return LocalDate.parse(str, dtf);
    }

    public List<Task> getTasks() {
        List<Task> tasksToReturn = new ArrayList<>();
        for (Integer id : tasks.keySet()) {
            tasksToReturn.add(tasks.get(id));
        }
        return tasksToReturn;
    }

    @Override
    public void assignDeadLine(int id, LocalDate dueDate) {
        if (tasks.containsKey(id)) {
            Task task = tasks.get(id);
            task.setDueDate(dueDate);
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
}
