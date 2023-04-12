package com.example.pkiservicebackend.repository;

import com.example.pkiservicebackend.model.CertificateData;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CertificateDataRepository extends JpaRepository<CertificateData, Long> {
}
