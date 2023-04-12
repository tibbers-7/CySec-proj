package com.example.pkiservicebackend.model;

import org.bouncycastle.asn1.x500.X500Name;

import java.security.PublicKey;
import java.util.Date;

public class SubjectData {

    private PublicKey publicKey;
    private X500Name x500name;
    public SubjectData() {

    }

    public SubjectData(PublicKey publicKey, X500Name x500name) {
        this.publicKey = publicKey;
        this.x500name = x500name;
    }

    public X500Name getX500name() {
        return x500name;
    }

    public void setX500name(X500Name x500name) {
        this.x500name = x500name;
    }

    public PublicKey getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(PublicKey publicKey) {
        this.publicKey = publicKey;
    }

}
