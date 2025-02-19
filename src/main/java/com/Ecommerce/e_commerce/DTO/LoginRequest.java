package com.Ecommerce.e_commerce.DTO;

import lombok.Data;

@Data
public class LoginRequest {
    private String UsernameOrEmail;
    private String password;

    public String getUsernameOrEmail() {
        return UsernameOrEmail;
    }

    public void setUsernameOrEmail(String usernameOrEmail) {
        UsernameOrEmail = usernameOrEmail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
