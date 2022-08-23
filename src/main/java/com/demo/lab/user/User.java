package com.demo.lab.user;

import com.demo.lab.base.Entity;

public class User extends Entity {
    private String username;
    private String password;
    private String token;
    private long tokenExpired;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public long getTokenExpired() {
        return tokenExpired;
    }

    public void setTokenExpired(long tokenExpired) {
        this.tokenExpired = tokenExpired;
    }
}
