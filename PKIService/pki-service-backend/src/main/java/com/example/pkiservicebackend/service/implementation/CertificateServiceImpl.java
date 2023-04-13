package com.example.pkiservicebackend.service.implementation;

import com.example.pkiservicebackend.certificate.CertificateGenerator;
import com.example.pkiservicebackend.certificate.CertificateRole;
import com.example.pkiservicebackend.certificate.CertificateStatus;
import com.example.pkiservicebackend.certificate.Generators;;
import com.example.pkiservicebackend.dto.GetAllCertificatesDTO;
import com.example.pkiservicebackend.dto.NewCertificateRequestDataDTO;
import com.example.pkiservicebackend.model.CertificateData;
import com.example.pkiservicebackend.model.DigitalEntity;
import com.example.pkiservicebackend.model.IssuerData;
import com.example.pkiservicebackend.model.SubjectData;
import com.example.pkiservicebackend.repository.CertificateDataRepository;
import com.example.pkiservicebackend.repository.DigitalEntityRepository;
import com.example.pkiservicebackend.service.CertificateService;
import com.example.pkiservicebackend.service.KeyStoreDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.math.BigInteger;
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
    private DigitalEntityRepository digitalEntityRepository;

    @Autowired
    private KeyStoreDataService keyStoreDataService;

    @Override
    public void issueCertificate(NewCertificateRequestDataDTO certData, String keyStorePassword) throws NoSuchAlgorithmException, CertificateException, NoSuchProviderException, KeyStoreException, IOException {




        DigitalEntity subject=certData.getSubject();
        DigitalEntity issuer=certData.getIssuer();

        // otvara keystore na osnovu role sertifikata
        String role = certData.getCertificateRole();
        if (this.keyStoreDataService.doesKeyStoreExist(role)){
            try {
                System.out.println("File found");
                KeyStore keyStore = KeyStore.getInstance("JKS", "SUN");
                keyStore.load(new FileInputStream("src/main/resources/keystores/" + role + ".jks"), keyStorePassword.toCharArray());
            } catch (IOException e) {
                System.out.println("Wrong password");
                throw new KeyStoreException();
            }
        }
///---------------------------------------------------------------------------------------------------------------------------------------------------------------------

        // provera da li se taj subject mejl vec nalazi u repou
        if (digitalEntityRepository.findByEmail(subject.getEmail()) != null) {
            System.out.println("Subject already exists!");
            //baci exception
        }

        Long issuerId;
        Long subjectId;

        // Nije SELF_SIGED issuer postoji u bazi, a subjecta treba snimiti
        if (!certData.getCertificateRole().equals("SELF_SIGNED")) {

            // provera da li je issuer istekao
            if (getCertificateStatus(issuer.getId())!=CertificateStatus.VALID){
                throw new InvalidObjectException("Issuer has expired!");
            }

            this.digitalEntityRepository.save(subject);
            this.digitalEntityRepository.flush();
        // SELF SIGNED - snimanje novog issuera u bazu; on je i issuer i subject, pa treba jednom snimiti
        } else {
            this.digitalEntityRepository.save(issuer);
            this.digitalEntityRepository.flush();

        }

        //---------------------------------------------------------------------------------
        // podesavanje id entiteta u cert

        issuerId = digitalEntityRepository.findByEmail(issuer.getEmail()).getId();
        if (certData.getCertificateRole().equals("SELF_SIGNED")) {
            subjectId = issuerId;
        } else {
            subjectId = digitalEntityRepository.findByEmail(subject.getEmail()).getId();
        }

        KeyPair keyPairIssuer = generators.generateKeyPair();

        CertificateRole certificateRole = CertificateRole.valueOf(certData.getCertificateRole());

        // kreiranje subjekta
        SubjectData subjectData = generators.generateSubjectData(subjectId, subject.getFirstName(), subject.getLastName(),subject.getOrganization(),
                subject.getOrganizationUnit(), subject.getCountry(), subject.getEmail(), certificateRole);

//-----------------------------------------------------------
        // KREIRANJE SERTIFIKATA
        CertificateData certificateDataToDB = new CertificateData(issuerId, subjectId, certificateRole, CertificateStatus.VALID, issuerId, null, null);


        // dodeljivanje exp perioda
        Calendar c = Calendar.getInstance();
        Date startDate = new Date();
        c.setTime(startDate);
        if(role.equals(CertificateRole.SELF_SIGNED)) {
            c.add(Calendar.YEAR, 30);
        } else if(role.equals(CertificateRole.INTERMEDIATE)){
            c.add(Calendar.YEAR, 20);
        } else {
            c.add(Calendar.YEAR, 10);
        }
        certificateDataToDB.setExpiringDate(c.getTime());

        //-------------------------------------------------------------------------------------

        // zapisivanje u bazu
        certificateDataToDB.setParent(issuerId);

        this.certificateDataRepository.save(certificateDataToDB);


        // ZAPISIVANJE U JKS

        IssuerData issuerData = generators.generateIssuerData(issuerId, keyPairIssuer.getPrivate(), issuer.getFirstName(), issuer.getLastName(),
                issuer.getOrganization(), issuer.getOrganizationUnit(), issuer.getCountry(), issuer.getEmail());


        // ovde ga ne napravi?
        X509Certificate certificate = certificateGenerator.generateCertificate(subjectData, issuerData,new BigInteger(String.valueOf(subjectId)),CertificateRole.valueOf(role));

        saveCertificate(certificateRole, "sifra", certificate.getSerialNumber().toString(), keyStorePassword, keyPairIssuer.getPrivate(), certificate);

        //--------------------------------------------------------------------------------------
        System.out.println("\n===== Certificate Issuer =====");
        System.out.println(certificate.getIssuerX500Principal().getName());
        System.out.println("\n===== Certificate Owner =====");
        System.out.println(certificate.getSubjectX500Principal().getName());
        System.out.println("\n===== Certificate =====");
        System.out.println("-------------------------------------------------------");
        System.out.println(certificate);
        System.out.println("-------------------------------------------------------");

    }

    @Override
    public void withdrawCertificate(Long certId) {

        // withdraw prvog u lancu
        CertificateData forWithdraw = this.certificateDataRepository.findById(certId).orElse(null);
        List<CertificateData> all = this.certificateDataRepository.findAll();

        forWithdraw.setCertificateStatus(CertificateStatus.REVOKED);
        this.certificateDataRepository.save(forWithdraw);
//---------------------------------------------------------------------------

        List<CertificateData> intermediate = new ArrayList<>();
        List<CertificateData> endEntity = new ArrayList<>();

        for (CertificateData certificate : all) {
            // self signed ne mora
            if (certificate.getCertificateRole().equals(CertificateRole.INTERMEDIATE)) {
                intermediate.add(certificate);
            } else if (certificate.getCertificateRole().equals(CertificateRole.END_ENTITY)) {
                endEntity.add(certificate);
            }
        }

        Set<Long> ids = new HashSet<>();
        ids.add(forWithdraw.getId());

        // ako nema posle njega onda zavrsava
        if (forWithdraw.getCertificateRole().equals(CertificateRole.END_ENTITY)) {
            System.out.println("Certifcate has no children");

            //prolazi kroz sve intermediate ispod njega i revokuje ih
        } else {
            for (CertificateData ca : intermediate) {
                for (Long id : ids) {
                    if (ca.getParent().equals(id)) {
                        ca.setCertificateStatus(CertificateStatus.REVOKED);
                        this.certificateDataRepository.save(ca);

                        // dopisuje da bi nastavio da revokuje nivoe ispod
                        ids.add(ca.getId());
                        break;
                    }
                }
            }

            //revokuje end entitije
            for (CertificateData ee : endEntity) {
                for (Long id : ids) {
                    if (ee.getParent().equals(id)) {
                        ee.setCertificateStatus(CertificateStatus.REVOKED);
                        this.certificateDataRepository.save(ee);
                        break;
                    }
                }
            }
        }
    }

    public void saveCertificate(CertificateRole role, String keyPassword, String alias, String keyStorePassword, PrivateKey privateKey, X509Certificate certificate) throws NoSuchProviderException, KeyStoreException, IOException, CertificateException, NoSuchAlgorithmException {
        String type = role.toString().toLowerCase();
        String file = ("src/main/resources/keystores/" + type + ".jks");
        KeyStore keyStore = KeyStore.getInstance("JKS", "SUN");

        try {
            keyStore.load(new FileInputStream(file), keyStorePassword.toCharArray());
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
            //fajl nepostoji, pravi novi
            createKeyStore(type, keyStorePassword, keyStore);
        } catch (IOException e) {
            System.out.println("Wrong password");
        } catch (NoSuchAlgorithmException | CertificateException e) {
            e.printStackTrace();
        }
        System.out.println("File size before write: " + keyStore.size());
        keyStore.setKeyEntry(alias, privateKey, keyPassword.toCharArray(), new Certificate[]{certificate}); //save cert
        System.out.println("File size after write: " + keyStore.size());
        keyStore.store(new FileOutputStream(file), keyStorePassword.toCharArray());
    }

    private void createKeyStore(String type, String keyStorePassword, KeyStore keyStore) {
        String file = ("src/main/resources/keystores/" + type + ".jks");
        try {
            keyStore.load(null, keyStorePassword.toCharArray());
            keyStore.store(new FileOutputStream(file), keyStorePassword.toCharArray());
        } catch (KeyStoreException | IOException | CertificateException | NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    @Override
    public CertificateStatus getCertificateStatus(Long id) {
        CertificateData certData = this.certificateDataRepository.findById(id).orElse(null);
        Date now = new Date();
        if (certData.getExpiringDate().before(now)) {
            certData.setCertificateStatus(CertificateStatus.EXPIRED);
            this.certificateDataRepository.save(certData);
        }
        return certData.getCertificateStatus();
    }

    @Override
    public Collection<DigitalEntity> getSSAndCa()  {
        return this.digitalEntityRepository.getSSAndCA();
    }

    @Override
    public Collection<GetAllCertificatesDTO> getCertificates() {
        Collection<CertificateData> certificates = this.certificateDataRepository.findAll();
        Collection<GetAllCertificatesDTO> dtos=new ArrayList<>();
        for (CertificateData cert:certificates) {
            GetAllCertificatesDTO dto=new GetAllCertificatesDTO(cert.getId(),cert.getSerialNumber(),cert.getCertificateRole().toString(),cert.getCertificateStatus().toString(),cert.getExpiringDate());
            DigitalEntity issuer=digitalEntityRepository.findById(cert.getIssuerId()).orElse(null);
            DigitalEntity subject=digitalEntityRepository.findById(cert.getSubjectId()).orElse(null);
            dto.setIssuer(issuer);
            dto.setSubject(subject);

            dtos.add(dto);

        }

        return dtos;
    }


}
