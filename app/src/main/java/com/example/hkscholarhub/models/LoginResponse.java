package com.example.hkscholarhub.models;

public class LoginResponse {
    private boolean status;
    private String message;
    private String userType;
    private String token;  // Add token field

    public boolean isStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public String getUserType() {
        return userType;
    }

    public String getToken() {
        return token;  // Add getter for the token
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }
}
