package com.example.apparkinglot.logic.Boundaries.User;



public class UserBoundary {
    private UserIdBoundary userId;
    private UserRole role;
    private String username;
    private String avatar;

    public UserBoundary() {
    }

    public UserBoundary(UserIdBoundary userId, UserRole role, String username, String avatar) {
        this.userId = userId;
        this.role = role;
        this.username = username;
        this.avatar = avatar;
    }

    public UserIdBoundary getUserId() {
        return userId;
    }

    public void setUserId(UserIdBoundary userId) {
        this.userId = userId;
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
