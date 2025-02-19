package com.Ecommerce.e_commerce.DTO;

public class ErrorDTO {
    private String erroMessage;
    private int statusCode;

    public ErrorDTO(String erroMessage, int statusCode) {
        this.erroMessage = erroMessage;
        this.statusCode = statusCode;
    }

    public String getErroMessage() {
        return erroMessage;
    }

    public void setErroMessage(String erroMessage) {
        this.erroMessage = erroMessage;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }
}
