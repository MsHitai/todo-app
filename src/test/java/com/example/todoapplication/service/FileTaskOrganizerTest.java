package com.example.todoapplication.service;

import com.example.todoapplication.model.Task;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.time.Month;

import static org.junit.jupiter.api.Assertions.*;

class FileTaskOrganizerTest {
    private File file;
    FileTaskOrganizer fileTaskOrganizer;

    @BeforeEach
    void setUp() {
        file = new File("resources/test_" + System.nanoTime() + ".csv");
        fileTaskOrganizer = new FileTaskOrganizer(file.toString());
        fileTaskOrganizer.addTask(fileTaskOrganizer.createTask("task1", "23-02-2022"));
        fileTaskOrganizer.addTask(fileTaskOrganizer.createTask("task2", "24-02-2022"));
        fileTaskOrganizer.addTask(fileTaskOrganizer.createTask("task3", "25-02-2022"));
    }

    @AfterEach
    protected void tearDown() {
        assertTrue(file.delete());
    }

    @Test
    public void createTask() {
        Assertions.assertEquals(3, fileTaskOrganizer.getTasks().size(), "Adding 3 tasks");
    }

    @Test
    public void assignDeadLine() {
        fileTaskOrganizer.assignDeadLine(2, "01-03-2023");
        Assertions.assertEquals(LocalDate.of(2023, Month.MARCH, 1),
                fileTaskOrganizer.getTasks().get(1).getDueDate(), "Changing date");
    }

    @Test
    public void markAsDone() {
        fileTaskOrganizer.markAsDone(2);
        Assertions.assertTrue(fileTaskOrganizer.getTasks().get(1).isDone(), "Marking 2 as done");
    }

    @Test
    public void removeTask() {
        fileTaskOrganizer.removeTask(1);
        Assertions.assertEquals(2, fileTaskOrganizer.getTasks().size(), "Removing 1 task");
    }

    @Test
    public void changeTask() {
        fileTaskOrganizer.changeTask(2, "work");
        Assertions.assertEquals("work", fileTaskOrganizer.getTasks().get(1).getDescription(), "Changing description");
    }

    @Test
    public void addTask() {
        fileTaskOrganizer.addTask(new Task());
        Assertions.assertEquals(4, fileTaskOrganizer.getTasks().size(), "Adding 1 task");
    }

    @Test // должен выбрасывать исключение при загрузке из неправ файла
    public void shouldThrowExceptionWithWrongPath () {
        assertThrows(FileNotFoundException.class, () -> FileTaskOrganizer.load("resources/wrongFile.csv"));
    }

    @Test // должен загружать из файла
    public void shouldLoadFromFile () throws FileNotFoundException {

        fileTaskOrganizer.addTask(fileTaskOrganizer.createTask("task4", "23-02-2022"));

        FileTaskOrganizer fileTaskOrganizer1 = FileTaskOrganizer.load(file.toString());

        assertEquals(4, fileTaskOrganizer1.getTasks().size());
    }
}