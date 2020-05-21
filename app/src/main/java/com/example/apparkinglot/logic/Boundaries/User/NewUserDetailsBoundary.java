package com.example.apparkinglot.logic.Boundaries.User;


public class NewUserDetailsBoundary {
    private String email;
    private UserRole role;
    private String username;
    private String avatar;

    public NewUserDetailsBoundary(String email, UserRole role, String username, String avatar) {
        this.email = email;
        this.role = role;
        this.username = username;
        this.avatar = avatar;
    }

    public NewUserDetailsBoundary() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
}
