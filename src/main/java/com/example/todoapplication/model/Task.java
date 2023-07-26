package com.example.todoapplication.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

@Entity
@Table(name = "tasks")
@Getter
@Setter
@NoArgsConstructor
@Builder
public class Task {
    @Id
    private int id;

    @Column(name = "description")
    private String description;

    @Column(name = "due_Date")
    private LocalDate dueDate;

    @Column(name = "is_done")
    private boolean isDone;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return id == task.id
                && isDone == task.isDone
                && Objects.equals(description, task.description)
                && Objects.equals(dueDate, task.dueDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, description, dueDate, isDone);
    }
}
