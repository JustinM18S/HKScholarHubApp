package com.example.hkscholarhub.models;

import java.util.List;

public class FacultiesResponse {
    private String message;
    private List<Faculty> faculties;

    public String getMessage() {
        return message;
    }

    public List<Faculty> getFaculties() {
        return faculties;
    }

    public static class Faculty {
        private String name;
        private String email;

        public Faculty(String name, String email) {
            this.name = name;
            this.email = email;
        }

        public String getName() {
            return name;
        }

        public String getEmail() {
            return email;
        }
    }
}
