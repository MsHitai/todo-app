package com.example.todoapplication.service;

import com.example.todoapplication.exceptions.ManagerSaveException;
import com.example.todoapplication.interfaces.Organizable;
import com.example.todoapplication.model.Task;

import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class FileTaskOrganizer extends TaskOrganizer implements Organizable {

    private final String path;

    public FileTaskOrganizer(String path) {
        super();
        this.path = path;
    }

    @Override
    public void addTask(Task task) {
        super.addTask(task);
        save();
    }

    @Override
    public Task createTask(String description, String date) {
        return super.createTask(description, date);
    }

    @Override
    public List<Task> getTasks() {
        return super.getTasks();
    }

    @Override
    public Task getTask(int id) {
        return super.getTask(id);
    }

    @Override
    public void assignDeadLine(int id, String dueDate) {
        super.assignDeadLine(id, dueDate);
        save();
    }

    @Override
    public void markAsDone(int id) {
        super.markAsDone(id);
        save();
    }

    @Override
    public void removeTask(int id) {
        super.removeTask(id);
        save();
    }

    @Override
    public void changeTask(int id, String description) {
        super.changeTask(id, description);
        save();
    }

    @Override
    public void updateTask(Task task) {
        super.updateTask(task);
        save();
    }

    @Override
    public void deleteAllTasks() {
        super.deleteAllTasks();
        save();
    }

    @Override
    public void deleteAllDoneTasks() {
        super.deleteAllDoneTasks();
        save();
    }

    private static Task taskFromString (String value) {
        String[] fields = value.split(",");
        int id = Integer.parseInt(fields[0]);
        String description = fields[1];
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate dueDate = LocalDate.parse(fields[2], dateTimeFormatter);
        boolean isDone = Boolean.parseBoolean(fields[3]);

        return new Task(id, description, dueDate, isDone);
    }

    private String separateTasksFromLists(List<Task> list) {
        StringBuilder sb = new StringBuilder();
        for (Task task : list) {
            sb.append(task.toString());
        }
        return sb.toString();
    }

    private void save() throws ManagerSaveException {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(this.path, false))) {
            bw.write("id,description,dueDate,isDone\n");
            bw.write(separateTasksFromLists(getTasks()));
        } catch (ManagerSaveException e) {
            throw new ManagerSaveException("Ошибка сохранения файла.");
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public static FileTaskOrganizer load (String path) throws FileNotFoundException {
        final FileTaskOrganizer fileTaskOrganizer = new FileTaskOrganizer(path);
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            while (br.ready()) {
                String line = br.readLine();
                if (!line.contains("id,description")) {
                    Task task = taskFromString(line);
                    fileTaskOrganizer.addTask(task);
                }
            }
        } catch (FileNotFoundException e) {
            throw new FileNotFoundException(e.getMessage());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return fileTaskOrganizer;
    }
}
