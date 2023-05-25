package com.example.bezbednostbackend.controller;

import com.example.bezbednostbackend.model.CV;
import com.example.bezbednostbackend.service.CVService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/cv")
public class CVController {
    @Autowired
    private CVService cvService;

    @GetMapping(value="/")
    public String getAll(Model model){
        List<CV> cvs = cvService.getFiles();
        model.addAttribute("cvs", cvs);
        return "cv";
    }

    @PostMapping(value="/uploadFile/{engineerID}")
    public String uploadFile(@RequestParam("file")MultipartFile file, @PathVariable("engineerID") Integer engineerID){
        cvService.saveFile(file, engineerID);
        return "ok";
    }

    @GetMapping("/downloadFile/{engineerID}")
    public ResponseEntity<ByteArrayResource> downloadFile(@PathVariable Integer engineerID){
        CV cv = cvService.getFileByEngineerID(engineerID).get();
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(cv.getDocType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment:filename=\""+cv.getDocName()+"\"")
                .body(new ByteArrayResource(cv.getData()));
    }
}
