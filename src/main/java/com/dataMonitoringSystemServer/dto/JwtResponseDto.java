package com.dataMonitoringSystemServer.dto;

import com.dataMonitoringSystemServer.entities.User;

import java.util.List;

public class JwtResponseDto {

    private String token;
    private String type = "Bearer";
    private User user ;
    private List<String> roles;

    /*
    public JwtResponseDto(String accessToken, Long id, String username, String email, List<String> roles) {
        this.token = accessToken;
        this.id = id;
        this.username = username;
        this.email = email;
        this.roles = roles;
    }

    public JwtResponseDto(String accessToken, String username, List<String> roles) {
        this.token = accessToken;
        this.username = username;
        this.roles = roles;
    }


     */

    public JwtResponseDto(String accessToken, User user, List<String> roles) {
        this.token = accessToken;
        this.user = user;
        this.roles = roles;
    }

    public String getAccessToken() {
        return token;
    }

    public void setAccessToken(String accessToken) {
        this.token = accessToken;
    }

    public String getTokenType() {
        return type;
    }

    public void setTokenType(String tokenType) {
        this.type = tokenType;
    }

    public User getUser() {
        return user;
    }

    public List<String> getRoles() {
        return roles;
    }


}
