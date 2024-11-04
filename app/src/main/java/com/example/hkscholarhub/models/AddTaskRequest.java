package com.example.hkscholarhub.models;

public class AddTaskRequest {
    private long student_id;
    private String task;
    private String duty_date;
    private String duty_start;
    private String duty_end;

    public AddTaskRequest(long student_id, String task, String duty_date, String duty_start, String duty_end) {
        this.student_id = student_id;
        this.task = task;
        this.duty_date = duty_date;
        this.duty_start = duty_start;
        this.duty_end = duty_end;
    }

    // Getters and setters...
}
