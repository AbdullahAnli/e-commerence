package com.ecommerce.auth.dto;

import com.ecommerce.user.dto.UserResponseDto;

public class LoginResponse {
    private String token;
    private String type = "Bearer";
    private UserResponseDto user;
    
    // Constructors
    public LoginResponse() {}
    
    public LoginResponse(String token, UserResponseDto user) {
        this.token = token;
        this.user = user;
    }
    
    // Getters and Setters
    public String getToken() { return token; }
    public void setToken(String token) { this.token = token; }
    
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
    
    public UserResponseDto getUser() { return user; }
    public void setUser(UserResponseDto user) { this.user = user; }
}
