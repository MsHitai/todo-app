package src.service;

import src.model.Task;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class TaskOrganizer implements Organizable{

    private int id;

    private Map<Integer, Task> tasks = new HashMap<>();

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
        scanner.nextLine();
        task.setDueDate(addDate(scanner));
        return task;
    }

    private String addDescription(Scanner scanner) {
        return scanner.nextLine();
    }

    private LocalDate addDate (Scanner scanner) { // добавить чтение информации с файла
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
