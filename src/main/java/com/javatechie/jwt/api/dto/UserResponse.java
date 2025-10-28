package com.javatechie.jwt.api.dto;

public class UserResponse {
    private final Integer id;
    private final String userName;
    private final String email;

    public UserResponse(Integer id, String userName, String email) {
        this.id = id;
        this.userName = userName;
        this.email = email;
    }

    public Integer getId() {
        return id;
    }

    public String getUserName() {
        return userName;
    }

    public String getEmail() {
        return email;
    }
}
