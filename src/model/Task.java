package model;

import java.time.LocalDate;

public class Task {
    private int id;
    private String description;
    private LocalDate dueDate;
    private boolean isDone;

    public Task() {
    }

    public Task(int id, String description, LocalDate dueDate, boolean isDone) {
        this.id = id;
        this.description = description;
        this.dueDate = dueDate;
        this.isDone = isDone;
    }

    public String getDescription() {
        return description;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public boolean isDone() {
        return isDone;
    }

    public void setDone(boolean done) {
        isDone = done;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    @Override
    public String toString() {
        return id + "," + description + "," + dueDate + "," + isDone + "\n";
    }
}
