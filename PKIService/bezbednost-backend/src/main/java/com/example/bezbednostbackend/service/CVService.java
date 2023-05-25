package com.example.bezbednostbackend.service;

import com.example.bezbednostbackend.model.CV;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

public interface CVService {
    CV saveFile(MultipartFile file, Integer engineerID);
    Optional<CV> getFileById(Integer fileID);
    Optional<CV> getFileByEngineerID(Integer engineerID);
    List<CV> getFiles();
}
