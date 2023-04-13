package com.example.pkiservicebackend.certificate;

import com.example.pkiservicebackend.model.IssuerData;
import com.example.pkiservicebackend.model.SubjectData;
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
import java.util.Calendar;
import java.util.Date;

public class CertificateGenerator {

    public CertificateGenerator() {
    }

    public X509Certificate generateCertificate(SubjectData subjectData, IssuerData issuerData, BigInteger certSerial,CertificateRole role) {
        try {
            JcaContentSignerBuilder builder = new JcaContentSignerBuilder("SHA256WithRSAEncryption");
            Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());

            builder = builder.setProvider("BC");

            ContentSigner contentSigner = builder.build(issuerData.getPrivateKey());

            Date startDate = new Date();

            Calendar c = Calendar.getInstance();
            c.setTime(startDate);
            if(role.equals(CertificateRole.SELF_SIGNED)) {
                c.add(Calendar.YEAR, 30);
            } else if(role.equals(CertificateRole.INTERMEDIATE)){
                c.add(Calendar.YEAR, 20);
            } else {
                c.add(Calendar.YEAR, 10);
            }

            Date endDate = c.getTime();


            X509v3CertificateBuilder certGen = new JcaX509v3CertificateBuilder(issuerData.getX500name(),
                    certSerial,
                    startDate,
                    endDate,
                    subjectData.getX500name(),
                    subjectData.getPublicKey());

            X509CertificateHolder certHolder = certGen.build(contentSigner);

            JcaX509CertificateConverter certConverter = new JcaX509CertificateConverter();

            // OVDE JE GRESKA
            Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());

            certConverter = certConverter.setProvider("BC");

            //Konvertuje objekat u sertifikat
            return certConverter.getCertificate(certHolder);
        } catch (OperatorCreationException | CertificateException e) {
        }
        return null;
    }
}
