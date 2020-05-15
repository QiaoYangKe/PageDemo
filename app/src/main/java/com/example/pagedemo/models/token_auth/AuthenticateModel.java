package com.example.pagedemo.models.token_auth;

public class AuthenticateModel {
    public AuthenticateModel(String userNameOrEmailAddress, String password, boolean rememberClient) {
        this.userNameOrEmailAddress = userNameOrEmailAddress;
        this.password = password;
        this.rememberClient = rememberClient;
    }

    private String userNameOrEmailAddress;

    private String password;

    private boolean rememberClient;

    public String getUserNameOrEmailAddress() {
        return userNameOrEmailAddress;
    }

    public void setUserNameOrEmailAddress(String userNameOrEmailAddress) {
        this.userNameOrEmailAddress = userNameOrEmailAddress;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean getRememberClient() {
        return rememberClient;
    }

    public void setRememberClient(boolean rememberClient) {
        this.rememberClient = rememberClient;
    }
}
