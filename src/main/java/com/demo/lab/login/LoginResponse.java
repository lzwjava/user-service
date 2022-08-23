package com.demo.lab.login;

import com.demo.lab.base.BaseResponse;

public class LoginResponse extends BaseResponse {
    private String token;
    private long tokenExpired;

    public LoginResponse(String token, long expired) {
        this.token = token;
        this.tokenExpired = expired;
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
