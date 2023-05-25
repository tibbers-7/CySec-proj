package com.example.bezbednostbackend.service.implementation;

import com.example.bezbednostbackend.model.CV;
import com.example.bezbednostbackend.repository.CVRepository;
import com.example.bezbednostbackend.service.CVService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@Service
public class CVServiceImpl implements CVService {

    @Autowired
    private CVRepository cvRepository;
    @Override
    public CV saveFile(MultipartFile file, Integer engineerID) {
        String docName = file.getOriginalFilename();
        try{
            CV cv = new CV(engineerID,docName, file.getContentType(),file.getBytes());
        } catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Optional<CV> getFileById(Integer fileID) {
        return cvRepository.findById(fileID);
    }

    @Override
    public Optional<CV> getFileByEngineerID(Integer engineerID) {
        return cvRepository.findByEngineerID(engineerID);
    }

    @Override
    public List<CV> getFiles() {
        return cvRepository.findAll();
    }
}
