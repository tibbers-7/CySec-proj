package com.example.pkiservicebackend.dto;

import com.example.pkiservicebackend.model.DigitalEntity;

import java.util.ArrayList;
import java.util.Date;

public class GetAllCertificatesDTO {
        private Long id;
        private DigitalEntity issuer;
        private DigitalEntity subject;
        private String serialNumber;
        private String certificateRole;
        private String certificateStatus;
        //private ArrayList<String> extensions;
        private Date expiringDate;

    public GetAllCertificatesDTO() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getCertificateRole() {
        return certificateRole;
    }

    public void setCertificateRole(String certificateRole) {
        this.certificateRole = certificateRole;
    }

    public String getCertificateStatus() {
        return certificateStatus;
    }

    public void setCertificateStatus(String certificateStatus) {
        this.certificateStatus = certificateStatus;
    }

    public Date getExpiringDate() {
        return expiringDate;
    }

    public void setExpiringDate(Date expiringDate) {
        this.expiringDate = expiringDate;
    }

    public DigitalEntity getIssuer() {
        return issuer;
    }

    public void setIssuer(DigitalEntity issuer) {
        this.issuer = issuer;
    }

    public DigitalEntity getSubject() {
        return subject;
    }

    public void setSubject(DigitalEntity subject) {
        this.subject = subject;
    }


}
