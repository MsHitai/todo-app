package src.test;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;
import src.model.Task;
import src.service.TaskOrganizer;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class Testing {

    TaskOrganizer taskOrganizer = new TaskOrganizer();

    @Before
    public void createTask() {
        taskOrganizer.addTask(taskOrganizer.createTask("task1", "23. 02. 2022"));
        taskOrganizer.addTask(taskOrganizer.createTask("task2", "24. 02. 2022"));
        taskOrganizer.addTask(taskOrganizer.createTask("task2", "25. 02. 2022"));
        assertEquals("Adding 3 tasks", 3, taskOrganizer.getTasks().size());
    }

    @Test
    public void assignDeadLine() {
        taskOrganizer.assignDeadLine(2, "01. 03. 2023");
        assertEquals("Changing date", LocalDate.parse("01. 03. 2023",
                DateTimeFormatter.ofPattern("dd. MM. yyyy")), taskOrganizer.getTasks().get(1).getDueDate());
    }                                                           // it's an arrayList, that's why it's index

    @Test
    public void markAsDone() {
        taskOrganizer.markAsDone(2);
        assertTrue("Marking 2 as done", taskOrganizer.getTasks().get(1).isDone());
    }

    @Test
    public void removeTask() {
        taskOrganizer.removeTask(1);
        assertEquals("Removing 1 task", 2, taskOrganizer.getTasks().size());
    }

    @Test
    public void changeTask() {
        taskOrganizer.changeTask(2, "work");
        assertEquals("Changing description", "work", taskOrganizer.getTasks().get(1).getDescription());
    }

    @Test
    public void addTask() {
        taskOrganizer.addTask(new Task());
        assertEquals("Adding 1 task", 4, taskOrganizer.getTasks().size());
    }

    public static void main(String[] args) {
        Result result = JUnitCore.runClasses(Testing.class);

        for (Failure fail : result.getFailures()) {
            System.out.println(fail.toString());
        }

        System.out.println(result.wasSuccessful());
    }
}

