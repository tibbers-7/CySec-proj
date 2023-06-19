package com.example.pkiservicebackend.certificate;

import com.example.pkiservicebackend.model.IssuerData;
import com.example.pkiservicebackend.model.SubjectData;
import org.bouncycastle.asn1.x509.AuthorityKeyIdentifier;
import org.bouncycastle.asn1.x509.BasicConstraints;
import org.bouncycastle.asn1.x509.Extension;
import org.bouncycastle.cert.CertIOException;
import org.bouncycastle.cert.X509CertificateHolder;
import org.bouncycastle.cert.X509v3CertificateBuilder;
import org.bouncycastle.cert.jcajce.JcaX509CertificateConverter;
import org.bouncycastle.cert.jcajce.JcaX509v3CertificateBuilder;
import org.bouncycastle.operator.ContentSigner;
import org.bouncycastle.operator.OperatorCreationException;
import org.bouncycastle.operator.jcajce.JcaContentSignerBuilder;

import java.math.BigInteger;
import java.security.Security;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import org.bouncycastle.asn1.x509.KeyUsage;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class CertificateGenerator {

    public CertificateGenerator() {
    }

    public X509Certificate generateCertificate(SubjectData subjectData, IssuerData issuerData, BigInteger certSerial, CertificateRole role, List<String> extensions) {
        try {
            JcaContentSignerBuilder builder = new JcaContentSignerBuilder("SHA256WithRSAEncryption");
            Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());

            builder = builder.setProvider("BC");

            ContentSigner contentSigner = builder.build(issuerData.getPrivateKey());

            Date startDate = new Date();

            Calendar c = Calendar.getInstance();
            c.setTime(startDate);
            if (role.equals(CertificateRole.SELF_SIGNED)) {
                c.add(Calendar.YEAR, 30);
            } else if (role.equals(CertificateRole.INTERMEDIATE)) {
                c.add(Calendar.YEAR, 20);
            } else {
                c.add(Calendar.YEAR, 10);
            }

            Date endDate = c.getTime();

            X509v3CertificateBuilder certGen = new JcaX509v3CertificateBuilder(
                    issuerData.getX500name(),
                    certSerial,
                    startDate,
                    endDate,
                    subjectData.getX500name(),
                    subjectData.getPublicKey());

            addExtensions(certGen, extensions, role, certSerial);

            X509CertificateHolder certHolder = certGen.build(contentSigner);

            JcaX509CertificateConverter certConverter = new JcaX509CertificateConverter();

            // OVDE JE GRESKA
            Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());

            certConverter = certConverter.setProvider("BC");

            //Konvertuje objekat u sertifikat
            return certConverter.getCertificate(certHolder);
        } catch (OperatorCreationException | CertificateException | CertIOException e) {
        }
        return null;
    }

    private static void addExtensions(X509v3CertificateBuilder certGen, List<String> extensions, CertificateRole role, BigInteger serial) throws CertIOException {
            KeyUsage usage;
            extensions.forEach(extension -> {
                switch (extension) {
                    //
                    case "Key Encipherment" -> {
                        try {
                            certGen.addExtension(Extension.keyUsage, false, new KeyUsage(KeyUsage.keyEncipherment));
                        } catch (CertIOException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    //
                    case "Data Encipherment" -> {
                        try {
                            certGen.addExtension(Extension.keyUsage, false, new KeyUsage(KeyUsage.dataEncipherment));
                        } catch (CertIOException e) {
                            throw new RuntimeException(e);
                        }
                    }
                   //
                    case "Key Agreement" -> {
                        try {
                            certGen.addExtension(Extension.keyUsage, false, new KeyUsage(KeyUsage.keyAgreement));
                        } catch (CertIOException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    //
                    case "Certificate Signing" -> {
                        try {
                            certGen.addExtension(Extension.keyUsage, false, new KeyUsage(KeyUsage.keyCertSign));
                        } catch (CertIOException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    //
                    default -> {
                        try {
                            certGen.addExtension(Extension.keyUsage, false, new KeyUsage(KeyUsage.digitalSignature));
                        } catch (CertIOException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }

            });
        //ovaj sertifikat moze da potpisuje druge sertifikate
        if (role.equals(CertificateRole.INTERMEDIATE) || role.equals(CertificateRole.SELF_SIGNED)) {
            certGen.addExtension(Extension.basicConstraints, true, new BasicConstraints(true));
        }
        //This extension helps identify the public key corresponding to the private key used to sign the certificate.
        // It provides a way to link a certificate to the issuing CA.
        AuthorityKeyIdentifier identifier = new AuthorityKeyIdentifier(
                serial.toByteArray());
        certGen.addExtension(Extension.authorityKeyIdentifier, true, identifier);

    }
}
