package com.example.pkiservicebackend.controller;

import com.example.pkiservicebackend.dto.NewCertificateRequestDataDTO;
import com.example.pkiservicebackend.service.CertificateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<?> issueCertificate(@RequestBody NewCertificateRequestDataDTO data, @PathVariable("keyStorePassword") String keyStorePassword) {
        try {
            this.certificateService.issueCertificate(data, keyStorePassword);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (IOException e) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        } catch (NoSuchAlgorithmException | CertificateException | NoSuchProviderException e) {
            e.printStackTrace();
        } catch (KeyStoreException e) {
            return new ResponseEntity<>("Password is incorrect! Please try again.", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PutMapping(value="/withdraw/{certId}")
    public ResponseEntity<?> withdrawCertificate(@PathVariable("certId") Long certId){
        try {
            this.certificateService.withdrawCertificate(certId);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = "/getSSAndCA")
    public ResponseEntity<?> getSSAndCa() {
        try {
            return new ResponseEntity<>(this.certificateService.getSSAndCa(), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @GetMapping(value = "/getCertificates")
    public ResponseEntity<?> getCertificates() {
        try {
            return new ResponseEntity<>(this.certificateService.getCertificates(), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }



}
