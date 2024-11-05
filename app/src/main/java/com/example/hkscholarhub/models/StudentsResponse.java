package com.example.hkscholarhub.models;

import java.util.ArrayList;
import java.util.List;

public class StudentsResponse {
    private List<Student> students;

    public List<User> getStudents() {
        // Convert List<Student> to List<User>
        List<User> users = new ArrayList<>();
        for (Student student : students) {
            users.add(new User(student.getId(), student.getName(), student.getEmail(), null, null));
        }
        return users;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }

    public static class Student {
        private long id;
        private String name;
        private String email;

        // Getter and Setter for id
        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }

        // Getter and Setter for name
        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        // Getter and Setter for email
        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }
    }
}
