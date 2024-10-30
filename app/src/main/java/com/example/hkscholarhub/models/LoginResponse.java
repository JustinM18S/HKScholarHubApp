package com.example.hkscholarhub.models;

public class LoginResponse {
    private String message;
    private String token;  // Add token field

    public UserObject getUser() {
        return user;
    }

    public void setUser(UserObject user) {
        this.user = user;
    }

    private UserObject user;


    public String getMessage() {
        return message;
    }


    public String getToken() {
        return token;  // Add getter for the token
    }


    public void setMessage(String message) {
        this.message = message;
    }

    public void setToken(String token) {
        this.token = token;
    }

}
