package com.example.pkiservicebackend.service.impl;

import com.example.pkiservicebackend.certificate.CertificateGenerator;
import com.example.pkiservicebackend.certificate.CertificateRole;
import com.example.pkiservicebackend.certificate.CertificateStatus;
import com.example.pkiservicebackend.certificate.Generators;
import com.example.pkiservicebackend.model.IssuerAndSubjectData;
import com.example.pkiservicebackend.model.IssuerData;
import com.example.pkiservicebackend.model.SubjectData;
import com.example.pkiservicebackend.repository.IssuerAndSubjectDataRepository;
import com.example.pkiservicebackend.service.CertificateService;
import com.example.pkiservicebackend.service.KeyStoreDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.NonUniqueResultException;
import java.io.*;
import java.security.*;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.time.LocalDateTime;
import java.util.*;


@Service
public class CertificateServiceImpl implements CertificateService {

    private Generators generators = new Generators();

    private CertificateGenerator certificateGenerator = new CertificateGenerator();

    @Autowired
    private CertificateDataRepository certificateDataRepository;

    @Autowired
    private KeyStoreDataService keyStoreDataService;

    @Override
    public void issueCertificate(NewCertificateRequestDataDTO certData) throws NoSuchAlgorithmException, CertificateException, NoSuchProviderException, KeyStoreException, IOException {


        DigitalEntity subject=certData.getSubject();
        DigitalEntity issuer=certData.getIssuer();

        // otvara keystore na osnovu role sertifikata
        String role=certData.getCertificateRole().toString());
        if (this.keyStoreDataService.doesKeyStoreExist(role) {
            try {
                System.out.println("File found");
                KeyStore keyStore = KeyStore.getInstance("JKS", "SUN");
                keyStore.load(new FileInputStream("keystores/" + role + ".jks"), keyStorePassword.toCharArray());
            } catch (IOException e) {
                System.out.println("Wrong password");
                throw new KeyStoreException();
            }
        }
///---------------------------------------------------------------------------------------------------------------------------------------------------------------------

        // provera da li se taj subject mejl vec nalazi u repou
        if (digitalEntityRepository.findByEmail(subject.getEmail()) != null) {
            System.out.println("Subject already exists!");
            throw new NonUniqueResultException();
        }

        Long issuerId;
        Long subjectId;

        // podesavanje roditelja za non ss
        if (certData.certificateRole!='SS') {

            Long parentId = digitalEntityRepository.findByEmail(issuer.getEmail()).getId();
            subject.setParent(parentId);

            this.digitalEntityRepository.save(subject);
            this.digitalEntityRepository.flush();

            // SELF SIGNED - snimanje novog entiteta u bazu
        } else {

            this.digitalEntityRepository.save(issuer);
            this.digitalEntityRepository.flush();

        }

        //---------------------------------------------------------------------------------
        // podesavanje id entiteta u cert

        issuerId = digitalEntityRepository.findByEmail(issuer.getEmail()).getId();
        if (certData.certificateRole=='SS') {
            subjectId = issuerId;
        } else {
            subjectId = issuerAndSubjectDataRepository.findByEmail(issuerAndSubjectData.getEmailSubject()).getId();
        }

        KeyPair keyPairIssuer = generators.generateKeyPair();

        // kreiranje subjekta
        SubjectData subjectData = generators.generateSubjectData(subjectId, subject.getFirstName(), subject.getLastName(),subject.getOrganization(),
                subject.getOrganizationUnit(), subject.getCountry(), subject.getEmail(), certData.getCertificateRole());


        // kreiranje sertifikata
        CertificateData certificate=new CertificateData(issuerId,role,'VALID',issuerId,certData.getStartDate(),certData.getEndDate()));

        certificate.setStartDate(subjectData.getStartDate());
        certificate.setExpiringDate(subjectData.getEndDate());
        if (role.equals('SELF_SIGNED')) {
            certificate.setParent(certificate.getId());
        }
        this.certificateDataRepository.save(certificate);

        IssuerData issuerData = generators.generateIssuerData(issuerId, keyPairIssuer.getPrivate(), issuer.getFirstName(), issuer.getLastName(),
                issuer.getOrganization(), issuer.getOrganizationUnit(), issuer.getCountry(), issuerAndSubjectData.getEmail());


        X509Certificate certificate = certificateGenerator.generateCertificate(subjectData, issuerData);

        saveCertificate(issuerAndSubjectData.getCertificateRole(), "sifra", certificate.getSerialNumber().toString(), keyStorePassword, keyPairIssuer.getPrivate(), certificate);

        //--------------------------------------------------------------------------------------
        System.out.println("\n===== Podaci o izdavacu sertifikata =====");
        System.out.println(certificate.getIssuerX500Principal().getName());
        System.out.println("\n===== Podaci o vlasniku sertifikata =====");
        System.out.println(certificate.getSubjectX500Principal().getName());
        System.out.println("\n===== Sertifikat =====");
        System.out.println("-------------------------------------------------------");
        System.out.println(certificate);
        System.out.println("-------------------------------------------------------");

    }


}
