package com.example.pkiservicebackend.model;

import com.example.pkiservicebackend.certificate.CertificateRole;
import com.example.pkiservicebackend.certificate.CertificateStatus;

import javax.persistence.*;
import java.util.Date;

@Entity
public class CertificateData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    private Long issuerId;
    private Long subjectId;

    @Enumerated(value = EnumType.STRING)
    private CertificateRole certificateRole;



    @Enumerated(value = EnumType.STRING)
    private CertificateStatus certificateStatus;

    @Column
    private Long parent;

    @Column
    Date startDate;

    @Column
    Date expiringDate;

    public CertificateData() {
    }

    public CertificateData(Long issuerId, Long subjectId, CertificateRole certificateRole, CertificateStatus certificateStatus, Long parent, Date startDate, Date expiringDate) {

        this.issuerId = issuerId;
        this.subjectId = subjectId;
        this.certificateRole = certificateRole;
        this.certificateStatus = certificateStatus;
        this.parent = parent;
        this.startDate = startDate;
        this.expiringDate = expiringDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIssuerId() {
        return issuerId;
    }

    public void setIssuerId(Long issuerId) {
        this.issuerId = issuerId;
    }

    public Long getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(Long subjectId) {
        this.subjectId = subjectId;
    }

    public CertificateRole getCertificateRole() {
        return certificateRole;
    }

    public void setCertificateRole(CertificateRole certificateRole) {
        this.certificateRole = certificateRole;
    }


    public CertificateStatus getCertificateStatus() {
        return certificateStatus;
    }

    public void setCertificateStatus(CertificateStatus certificateStatus) {
        this.certificateStatus = certificateStatus;
    }

    public Long getParent() {
        return parent;
    }

    public void setParent(Long parent) {
        this.parent = parent;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getExpiringDate() {
        return expiringDate;
    }

    public void setExpiringDate(Date expiringDate) {
        this.expiringDate = expiringDate;
    }
}
