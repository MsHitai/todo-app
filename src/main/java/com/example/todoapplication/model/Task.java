package com.example.todoapplication.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Entity
@Table(name = "tasks")
@Data
@Builder
public class Task {
    @Id
    private int id;
    @Column(name = "description")
    private String description;
    @Column(name = "due_Date")
    private LocalDate dueDate;
    @Column(name = "isDone")
    private boolean isDone;

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
