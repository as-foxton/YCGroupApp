package nl.yc2306.recruitmentApp.security;

import java.io.Serializable;

public class LoginResponse implements Serializable{
    private final String token;

    public LoginResponse(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }
}
