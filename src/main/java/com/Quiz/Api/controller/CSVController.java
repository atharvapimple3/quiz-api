package com.Quiz.Api.controller;

import com.Quiz.Api.service.CSVService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/csv")
public class CSVController {

    CSVService csvService;

    @Autowired
    public CSVController(CSVService csvService) {
        this.csvService = csvService;
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/upload/{quizId}")
    public ResponseEntity<String> uploadFile(@RequestParam("file")MultipartFile multipartFile, @PathVariable Integer quizId){
        try {
            csvService.saveFile(multipartFile,quizId);
        }catch (Exception e){
            throw new RuntimeException("File cannot be uploaded",e);
        }
        return ResponseEntity.status(HttpStatus.ACCEPTED).body("File uploaded");
    }


}
