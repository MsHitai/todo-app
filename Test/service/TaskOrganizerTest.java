package service;

import model.Task;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

class TaskOrganizerTest {

    TaskOrganizer taskOrganizer = new TaskOrganizer();

    @BeforeEach
    void setUp() {
        taskOrganizer.addTask(taskOrganizer.createTask("task1", "23. 02. 2022"));
        taskOrganizer.addTask(taskOrganizer.createTask("task2", "24. 02. 2022"));
        taskOrganizer.addTask(taskOrganizer.createTask("task2", "25. 02. 2022"));
    }

    @Test
    public void createTask() {
        Assertions.assertEquals(3, taskOrganizer.getTasks().size(), "Adding 3 tasks");
    }

    @Test
    public void assignDeadLine() {
        taskOrganizer.assignDeadLine(2, "01. 03. 2023");
        Assertions.assertEquals(LocalDate.parse("01. 03. 2023",
                DateTimeFormatter.ofPattern("dd. MM. yyyy")), taskOrganizer.getTasks().get(1).getDueDate(), "Changing date");
    }                                                           // it's an arrayList, that's why it's an index

    @Test
    public void markAsDone() {
        taskOrganizer.markAsDone(2);
        Assertions.assertTrue(taskOrganizer.getTasks().get(1).isDone(), "Marking 2 as done");
    }

    @Test
    public void removeTask() {
        taskOrganizer.removeTask(1);
        Assertions.assertEquals(2, taskOrganizer.getTasks().size(), "Removing 1 task");
    }

    @Test
    public void changeTask() {
        taskOrganizer.changeTask(2, "work");
        Assertions.assertEquals("work", taskOrganizer.getTasks().get(1).getDescription(), "Changing description");
    }

    @Test
    public void addTask() {
        taskOrganizer.addTask(new Task());
        Assertions.assertEquals(4, taskOrganizer.getTasks().size(), "Adding 1 task");
    }
}