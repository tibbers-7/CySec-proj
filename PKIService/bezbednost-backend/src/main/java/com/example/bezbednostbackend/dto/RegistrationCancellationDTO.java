package com.example.bezbednostbackend.dto;

public class RegistrationCancellationDTO {
    private String cancellationDescription;
    private Integer idOfRequest;

    public RegistrationCancellationDTO(String cancellationDescription, Integer idOfRequest) {
        this.cancellationDescription = cancellationDescription;
        this.idOfRequest = idOfRequest;
    }

    public String getCancellationDescription() {
        return cancellationDescription;
    }

    public void setCancellationDescription(String cancellationDescription) {
        this.cancellationDescription = cancellationDescription;
    }

    public Integer getIdOfRequest() {
        return idOfRequest;
    }

    public void setIdOfRequest(Integer idOfRequest) {
        this.idOfRequest = idOfRequest;
    }
}
