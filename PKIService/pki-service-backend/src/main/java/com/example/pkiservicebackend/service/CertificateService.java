package com.example.pkiservicebackend.service;

import com.example.pkiservicebackend.certificate.CertificateStatus;
import com.example.pkiservicebackend.dto.NewCertificateRequestDataDTO;
import com.example.pkiservicebackend.model.DigitalEntity;

import java.io.IOException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.cert.CertificateException;
import java.util.Collection;

public interface CertificateService {
    void issueCertificate(NewCertificateRequestDataDTO certData, String keyStorePassword) throws NoSuchAlgorithmException, CertificateException, NoSuchProviderException, KeyStoreException, IOException;
    void withdrawCertificate(Long certId);
    CertificateStatus getCertificateStatus(Long id);

    Collection<DigitalEntity> getSSAndCa();


}
