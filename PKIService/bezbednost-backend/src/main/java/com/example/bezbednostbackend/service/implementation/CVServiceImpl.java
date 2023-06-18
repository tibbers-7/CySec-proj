package com.example.bezbednostbackend.service.implementation;

import com.example.bezbednostbackend.model.CV;
import com.example.bezbednostbackend.repository.CVRepository;
import com.example.bezbednostbackend.service.CVService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

@Service
public class CVServiceImpl implements CVService {

    @Autowired
    private CVRepository cvRepository;

    @Override
    public String saveFile(MultipartFile file, Integer engineerID) {
        CV cv = null;
        try {
            cv = cvRepository.save(CV.builder()
                    .engineerID(engineerID)
                    .docName(file.getOriginalFilename())
                    .docType(file.getContentType())
                    .docData(compressFile(file.getBytes())).build());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        if(cv != null){
            return "file uploaded successfully : " + file.getOriginalFilename();
        }
        return null;
    }

    @Override
    public CV downloadFile(Integer engineerID){
        Optional<CV> dbCv = cvRepository.findByEngineerID(engineerID);
        if(!dbCv.isEmpty()){
            CV cv = new CV(dbCv.get().getId(), dbCv.get().getEngineerID(), dbCv.get().getDocName(), dbCv.get().getDocType(), decompressFile(dbCv.get().getDocData()));
            return cv;
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

    public static byte[] compressFile(byte[] data) {
        Deflater deflater = new Deflater();
        deflater.setLevel(Deflater.BEST_COMPRESSION);
        deflater.setInput(data);
        deflater.finish();

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
        byte[] tmp = new byte[4*1024];
        while (!deflater.finished()) {
            int size = deflater.deflate(tmp);
            outputStream.write(tmp, 0, size);
        }
        try {
            outputStream.close();
        } catch (Exception ignored) {
        }
        return outputStream.toByteArray();
    }

    public static byte[] decompressFile(byte[] data) {
        Inflater inflater = new Inflater();
        inflater.setInput(data);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
        byte[] tmp = new byte[4*1024];
        try {
            while (!inflater.finished()) {
                int count = inflater.inflate(tmp);
                outputStream.write(tmp, 0, count);
            }
            outputStream.close();
        } catch (Exception ignored) {
        }
        return outputStream.toByteArray();
    }
}