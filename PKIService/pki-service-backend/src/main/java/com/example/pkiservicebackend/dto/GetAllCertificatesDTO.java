package com.example.pkiservicebackend.dto;

import com.example.pkiservicebackend.model.DigitalEntity;

import java.util.Date;

public class GetAllCertificatesDTO {
        private Long id;
        private DigitalEntity issuer;
        private DigitalEntity subject;
        private String serialNumber;
        private String certificateRole;
        private String certificateStatus;
        private Date expiringDate;

    public GetAllCertificatesDTO(Long id, DigitalEntity issuer, DigitalEntity subject, String serialNumber, String certificateRole, String certificateStatus, Date expiringDate) {
        this.id = id;
        this.issuer = issuer;
        this.subject = subject;
        this.serialNumber = serialNumber;
        this.certificateRole = certificateRole;
        this.certificateStatus = certificateStatus;
        this.expiringDate = expiringDate;
    }
    public GetAllCertificatesDTO(){}


}
