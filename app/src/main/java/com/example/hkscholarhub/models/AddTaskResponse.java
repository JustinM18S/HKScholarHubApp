package com.example.hkscholarhub.models;

public class AddTaskResponse {
    private String message;
    private Task task;

    public String getMessage() {
        return message;
    }

    public Task getTask() {
        return task;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setTask(Task task) {
        this.task = task;
    }

    public static class Task {
        private long id;
        private String task;
        private String duty_date;
        private String duty_start;
        private String duty_end;

        // Getters and setters...
    }
}
