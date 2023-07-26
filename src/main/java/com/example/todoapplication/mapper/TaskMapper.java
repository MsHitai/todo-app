package com.example.todoapplication.mapper;

import com.example.todoapplication.dto.TaskDto;
import com.example.todoapplication.model.Task;
import lombok.experimental.UtilityClass;

@UtilityClass
public class TaskMapper {

    public TaskDto mapToTaskDto(Task task) {
        return TaskDto.builder()
                .id(task.getId())
                .description(task.getDescription())
                .dueDate(task.getDueDate())
                .isDone(task.isDone())
                .build();
    }

    public Task mapToTask(TaskDto taskDto) {
        return Task.builder()
                .id(taskDto.getId())
                .description(taskDto.getDescription())
                .dueDate(taskDto.getDueDate())
                .isDone(taskDto.isDone())
                .build();
    }
}
