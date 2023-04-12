package com.example.pkiservicebackend.controller;

import com.example.pkiservicebackend.service.KeyStoreDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.cert.X509Certificate;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping(value = "/api/keyStoreData")
public class KeyStoreDataController {

    @Autowired
    private KeyStoreDataService keyStoreDataService;

    @GetMapping(value = "/doesKeyStoreExist/{certificateRole}")
    public ResponseEntity<?> doesKeyStoreExist(@PathVariable("certificateRole") String certificateRole) {
        return new ResponseEntity<>(this.keyStoreDataService.doesKeyStoreExist(certificateRole), HttpStatus.OK);
    }

    @GetMapping(value = "/loadCertificate/{role}/{alias}/{password}")
    public ResponseEntity<?> loadCertificate(@PathVariable("role") String role, @PathVariable("alias") String alias, @PathVariable("password") String password) {
        try {

            X509Certificate certificate = this.keyStoreDataService.loadCertificate(role, alias, password);
            if (certificate == null) {
                return new ResponseEntity<>("Certificate with this alias doesn't exist.", HttpStatus.BAD_REQUEST);
            }

            return new ResponseEntity<>(certificate.getSubjectDN(), HttpStatus.OK);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>("Password is incorrect!", HttpStatus.BAD_REQUEST);
    }
}
