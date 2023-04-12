package com.example.pkiservicebackend.service.implementation;

import com.example.pkiservicebackend.service.KeyStoreDataService;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

@Service
public class KeyStoreDataServiceImpl implements KeyStoreDataService {

    @Override
    public boolean doesKeyStoreExist(String certificateRole) {
        String name = certificateRole.toLowerCase();
        File file = new File("keystores/" + name + ".jks");
        return file.exists();
    }

    @Override
    public X509Certificate loadCertificate(String role, String alias, String password) throws NoSuchProviderException, KeyStoreException, IOException, CertificateException, NoSuchAlgorithmException {
        //kreira instancu KeyStore
        KeyStore ks = KeyStore.getInstance("JKS", "SUN");
        //ucitava podatke
        String keyStoreFile = "keystores/" + role.toLowerCase() + ".jks";
        ks.load(new FileInputStream(keyStoreFile), password.toCharArray());

        System.out.println("Key storage size: " + ks.size());
        System.out.println("File alias: " + alias);
        if (ks.containsAlias(alias)) {
            return (X509Certificate) ks.getCertificate(alias);
        }
        return null;
    }


}
