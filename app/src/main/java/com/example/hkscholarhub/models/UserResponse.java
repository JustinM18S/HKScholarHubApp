package com.example.hkscholarhub.models;

public class UserResponse {
    private String message;
    private User user;

    public String getMessage() {
        return message;
    }

    public User getUser() {
        return user;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public static class User {
        private int id;
        private String name;
        private String email;
        private String user_type;

        public User(String name, String email) {
        }

        // Getters for user fields
        public int getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public String getEmail() {
            return email;
        }

        public String getUserType() {
            return user_type;
        }

        public void setId(int id) {
            this.id = id;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public void setUser_type(String user_type) {
            this.user_type = user_type;
        }
    }
}
