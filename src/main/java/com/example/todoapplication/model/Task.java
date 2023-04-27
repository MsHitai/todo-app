package com.example.todoapplication.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

@Entity
@Table(name = "TASK")
public class Task {
    @Id
    private int id;
    @NotNull(message = "Задача должна содержать описание")
    private String description;
    @FutureOrPresent(message = "Дата окончания задачи должна быть в настоящем или будущем времени")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    @Column(name = "due_Date")
    private LocalDate dueDate;
    @Column(name = "isDone")
    private boolean isDone;

    /*private String username;

    public Task(int id, String description, LocalDate dueDate, boolean isDone, String username) {
        this.id = id;
        this.description = description;
        this.dueDate = dueDate;
        this.isDone = isDone;
        this.username = username;
    }*/

    public Task() {

    }
    @JsonCreator
    public Task(@JsonProperty("id") int id, @JsonProperty("description") @NotNull String description,
                @JsonProperty("dueDate") @FutureOrPresent LocalDate dueDate, @JsonProperty("isDone") boolean isDone) {
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

    /*public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }*/

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return id == task.id
                && isDone == task.isDone
                && description.equals(task.description)
                && dueDate.equals(task.dueDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, description, dueDate, isDone);
    }

    @Override
    public String toString() {
        String formattedTaskDate;

        if (dueDate == null) {
            formattedTaskDate = null;
        } else {
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            formattedTaskDate = dueDate.format(dtf);
        }

        return id + "," + description + "," + formattedTaskDate + "," + isDone + "\n";
    }
}
