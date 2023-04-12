package com.example.pkiservicebackend.controller;

import com.example.pkiservicebackend.certificate.CertificateStatus;
import com.example.pkiservicebackend.model.IssuerAndSubjectData;
import com.example.pkiservicebackend.service.CertificateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.persistence.NonUniqueResultException;
import java.io.IOException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.cert.CertificateException;


@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping(value = "/api/certificates")
public class CertificateController {

    @Autowired
    private CertificateService certificateService;

    @PostMapping(value = "/issueCertificate/{keyStorePassword}")
    public ResponseEntity<?> issueCertificate(@RequestBody NewCertificateRequestData data, @PathVariable("keyStorePassword") String keyStorePassword) {
        try {
            this.certificateService.issueCertificate(data, keyStorePassword);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (IOException e) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        } catch (NoSuchAlgorithmException | CertificateException | NoSuchProviderException e) {
            e.printStackTrace();
        } catch (KeyStoreException e) {
            return new ResponseEntity<>("Password is incorrect! Please try again.", HttpStatus.BAD_REQUEST);
        } catch (NonUniqueResultException e) {
            return new ResponseEntity<>("Certificate with this email already exists. Enter another one.", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }



}
