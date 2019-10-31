package com.feng.sportsone.service.model;

public class LoginStatus {
    boolean login;
    boolean pwdChange;
    String username;
    String message;

    public boolean isLogin() {
        return login;
    }

    public void setLogin(boolean login) {
        this.login = login;
    }

    public boolean isPwdChange() {
        return pwdChange;
    }

    public void setPwdChange(boolean pwdChange) {
        this.pwdChange = pwdChange;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}