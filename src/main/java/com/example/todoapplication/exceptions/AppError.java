package com.example.todoapplication.exceptions;

public class AppError extends RuntimeException {

    public AppError(String message) {
        super(message);
    }
}
