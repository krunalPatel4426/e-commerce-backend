package com.Ecommerce.e_commerce.DTO;

import java.util.UUID;

public class UserDTO {
    private UUID id;
    private String username;
    private String email;
    private String name;
    private String phone;
    private String address;

    public UserDTO(UUID id, String username, String email, String name, String phone, String address) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.name = name;
        this.phone = phone;
        this.address = address;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
