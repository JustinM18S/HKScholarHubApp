package com.example.hkscholarhub.models;

import java.util.List;

public class DutiesResponse {
    private String message;
    private List<Duty> tasks;

    // Getters and Setters
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<Duty> getTasks() {
        return tasks;
    }

    public void setTasks(List<Duty> tasks) {
        this.tasks = tasks;
    }
}
