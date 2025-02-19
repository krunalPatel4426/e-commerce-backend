package com.Ecommerce.e_commerce.DTO;

public class UpdateAddressReqDTO {
    String address;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public UpdateAddressReqDTO(String address) {
        this.address = address;
    }
}
