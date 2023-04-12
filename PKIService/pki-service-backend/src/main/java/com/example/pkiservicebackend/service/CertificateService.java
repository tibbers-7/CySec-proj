package com.example.pkiservicebackend.service;

import com.example.pkiservicebackend.dto.NewCertificateRequestDataDTO;

import java.io.IOException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.cert.CertificateException;

public interface CertificateService {
    void issueCertificate(NewCertificateRequestDataDTO certData, String keyStorePassword) throws NoSuchAlgorithmException, CertificateException, NoSuchProviderException, KeyStoreException, IOException;


}
