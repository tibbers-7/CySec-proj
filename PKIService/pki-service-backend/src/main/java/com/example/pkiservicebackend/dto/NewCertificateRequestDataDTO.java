package com.example.pkiservicebackend.dto;

import com.example.pkiservicebackend.model.DigitalEntity;

import java.util.ArrayList;
import java.util.Date;

public class NewCertificateRequestDataDTO {

    private Long id;
    private DigitalEntity issuer;
    private DigitalEntity subject;
    private String certificateRole;
    private ArrayList<String> extensions;

    public NewCertificateRequestDataDTO() {
    }

    public NewCertificateRequestDataDTO(Long id, DigitalEntity issuer, DigitalEntity subject, String certificateRole, ArrayList<String> extensions) {
        this.id = id;
        this.issuer = issuer;
        this.subject = subject;
        this.certificateRole = certificateRole;
        this.extensions = extensions;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getCertificateRole() {
        return certificateRole;
    }

    public void setCertificateRole(String certificateRole) {
        this.certificateRole = certificateRole;
    }

    public ArrayList<String> getExtensions() {
        return extensions;
    }

    public void setExtensions(ArrayList<String> extensions) {
        this.extensions = extensions;
    }
}
