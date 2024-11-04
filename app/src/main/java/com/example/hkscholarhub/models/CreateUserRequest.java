package com.example.hkscholarhub.models;

public class CreateUserRequest {
    private final String name;
    private final String student_id;
    private final String faculty_id;
    private final String email;
    private final String password;
    private final String password_confirmation;
    private final String user_type;
    private final String hk_type;

    public CreateUserRequest(String name, String email, String password,String password_confirmation, String user_type, String student_id, String faculty_id, String hk_type) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.password_confirmation = password_confirmation;
        this.user_type = user_type;
        this.student_id = student_id;
        this.faculty_id = faculty_id;
        this.hk_type = hk_type;
    }

    public String getName() {
        return name;
    }

    public String getStudent_id() {
        return student_id;
    }

    public String getUser_type() {
        return user_type;
    }

    public String getFaculty_id() {
        return faculty_id;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
    public String getPassword_confirmation() {
        return password_confirmation;
    }
    public String getHk_type() {
        return hk_type;
    }
}
