package com.example.apparkinglot.logic.Boundaries.Action;


import com.example.apparkinglot.logic.Boundaries.User.UserIdBoundary;

public class InvokingUser {
    private UserIdBoundary userId;

    public InvokingUser(UserIdBoundary userId) {
        this.userId = userId;
    }

    public InvokingUser() {
    }

    public UserIdBoundary getUserId() {
        return userId;
    }

    public void setUserId(UserIdBoundary userId) {
        this.userId = userId;
    }
}
