package com.example.hkscholarhub.models;

public class Duty {
    private long id;
    private String student_id;
    private String task;
    private String duty_date;
    private String duty_start;
    private String duty_end;
    private String status;

    // Getters and Setters

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getStudentId() {
        return student_id;
    }

    public void setStudentId(String student_id) {
        this.student_id = student_id;
    }

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public String getDutyDate() {
        return duty_date;
    }

    public void setDutyDate(String duty_date) {
        this.duty_date = duty_date;
    }

    public String getDutyStart() {
        return duty_start;
    }

    public void setDutyStart(String duty_start) {
        this.duty_start = duty_start;
    }

    public String getDutyEnd() {
        return duty_end;
    }

    public void setDutyEnd(String duty_end) {
        this.duty_end = duty_end;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
