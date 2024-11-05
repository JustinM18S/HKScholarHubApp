package com.example.hkscholarhub.models;

public class AddTaskResponse {
    private String message;
    private Task task;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }

    public static class Task {
        private long id;
        private long student_id;  // Added student_id field for consistency
        private String task;
        private String duty_date;
        private String duty_start;
        private String duty_end;

        // Getters and Setters for all fields
        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }

        public long getStudent_id() {
            return student_id;
        }

        public void setStudent_id(long student_id) {
            this.student_id = student_id;
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
}
