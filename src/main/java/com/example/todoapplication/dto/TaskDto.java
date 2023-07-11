package com.example.todoapplication.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class TaskDto {

    private int id;
    @NotNull(message = "Задача должна содержать описание")
    private String description;
    @FutureOrPresent(message = "Дата окончания задачи должна быть в настоящем или будущем времени")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private LocalDate dueDate;
    private boolean isDone;
}
