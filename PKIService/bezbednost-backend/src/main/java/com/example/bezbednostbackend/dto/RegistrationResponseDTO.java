package com.example.bezbednostbackend.dto;

public class RegistrationResponseDTO {
    private String response;
    private boolean isValid;

    public RegistrationResponseDTO(String response, boolean isValid) {
        this.response = response;
        this.isValid = isValid;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public boolean isValid() {
        return isValid;
    }

    public void setValid(boolean valid) {
        isValid = valid;
    }
}
