package com.jie.de.model.dto;

public class LoginResponseDTO {

    private String token;
    private String message;
    private String username;
    private Long userId;

    public LoginResponseDTO(String token, String message, String username, Long userId) {
        this.token = token;
        this.message = message;
        this.username = username;
        this.userId = userId;
    }

    public String getUsername() {
        return this.username;
    }

    public String getToken() {
        return this.token;
    }

    public String getMessage() {
        return this.message;
    }

    public Long getUserId() {
        return this.userId;
    }

}
