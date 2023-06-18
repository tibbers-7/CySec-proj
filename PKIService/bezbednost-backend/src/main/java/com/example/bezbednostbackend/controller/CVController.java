package com.example.bezbednostbackend.controller;

import com.example.bezbednostbackend.dto.StringResponseDTO;
import com.example.bezbednostbackend.model.CV;
import com.example.bezbednostbackend.service.CVService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/cv")
@CrossOrigin("http://localhost:4200")
public class CVController {
    @Autowired
    private CVService cvService;


    @PreAuthorize("hasAuthority('GET_ALL_CVS')")
    @GetMapping(value="/")
    public String getAll(Model model){
        List<CV> cvs = cvService.getFiles();
        model.addAttribute("cvs", cvs);
        return "cv";
    }

    @PreAuthorize("hasAuthority('UPLOAD_CV')")
    @PostMapping(value="/uploadFile/{engineerID}", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    public ResponseEntity<StringResponseDTO> uploadFile(@RequestParam("pdfFile")MultipartFile file, @PathVariable("engineerID") Integer engineerID){
        String uploadCv = cvService.saveFile(file, engineerID);
        return new ResponseEntity<>(new StringResponseDTO("File uploaded!"), HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('DOWNLOAD_CV')")
    @GetMapping(value="/downloadFile/{engineerID}")
    public CV downloadFile(@PathVariable Integer engineerID){
        CV cv = cvService.downloadFile(engineerID);
        if(cv != null){
            return cv;
        }
        return null;
    }
}
