package com.example.hkscholarhub.models;

public class AddTaskRequest {
    private final long student_id;
    private String task;  // Added the missing task field
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

    public long getStudent_id() {
        return student_id;
    }

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public String getDuty_date() {
        return duty_date;
    }

    public void setDuty_date(String duty_date) {
        this.duty_date = duty_date;
    }

    public String getDuty_start() {
        return duty_start;
    }

    public void setDuty_start(String duty_start) {
        this.duty_start = duty_start;
    }

    public String getDuty_end() {
        return duty_end;
    }

    public void setDuty_end(String duty_end) {
        this.duty_end = duty_end;
    }
}
