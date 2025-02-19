package com.Ecommerce.e_commerce.DTO;

import com.Ecommerce.e_commerce.model.User;

import java.util.UUID;

public class LoginDTO {
    private String message;
    private String auth_token;
    private UserDTO user;
    private String status;

    public LoginDTO(String message, String auth_token, UserDTO user, String status) {
        this.message = message;
        this.auth_token = auth_token;
        this.user = user;
        this.status = status;
    }

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getAuth_token() {
        return auth_token;
    }

    public void setAuth_token(String auth_token) {
        this.auth_token = auth_token;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
