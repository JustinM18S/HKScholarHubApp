package com.example.hkscholarhub.models;

import java.util.List;

public class FacultiesResponse {
    private List<Faculty> faculties;

    // Constructor, if needed
    public FacultiesResponse(List<Faculty> faculties) {
        this.faculties = faculties;
    }

    // Getter for the list of faculties
    public List<Faculty> getFaculties() {
        return faculties;
    }

    // Inner class for Faculty
    public static class Faculty {
        private long id;
        private String name;
        private String email;

        // Constructor
        public Faculty(long id, String name, String email) {
            this.id = id;
            this.name = name;
            this.email = email;
        }

        // Getters
        public long getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public String getEmail() {
            return email;
        }

        // Override toString to return the name of the faculty
        @Override
        public String toString() {
            return name;
        }
    }

}
