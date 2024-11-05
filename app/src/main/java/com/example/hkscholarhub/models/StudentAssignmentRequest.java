package com.example.hkscholarhub.models;

public class StudentAssignmentRequest {
    private long student_id;
    private long faculty_id;
    private String hk_type;
    private String hk_duty_type;

    public StudentAssignmentRequest(long student_id, long faculty_id, String hk_type, String hk_duty_type) {
        this.student_id = student_id;
        this.faculty_id = faculty_id;
        this.hk_type = hk_type;
        this.hk_duty_type = hk_duty_type;
    }

    // Getters and setters, if necessary
}

