package com.example.user;

public class UserInfo {
    private String userName;
    private int credits;

    public UserInfo(String userName, int credits) {
        this.userName = userName;
        this.credits = credits;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getCredits() {
        return credits;
    }

    public void setCredits(int credits) {
        this.credits = credits;
    }
}
