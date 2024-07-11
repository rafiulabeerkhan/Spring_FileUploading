package com.DbFileStoring.storage_service.controller;

import com.DbFileStoring.storage_service.entity.FileData;
import com.DbFileStoring.storage_service.repository.FileDataRepository;
import com.DbFileStoring.storage_service.service.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@RestController
@RequestMapping("/image")
public class StorageController {
    @Autowired
    private StorageService service;

    @Autowired
    private FileDataRepository fileDataRepository;

    @PostMapping(value = "/fileSystem")
    public ResponseEntity<?> uploadImageToFileSystem(@RequestParam("image")MultipartFile file) throws IOException {
        String uploadImage = service.uploadImageToFileSystem(file);
        return ResponseEntity.status(HttpStatus.OK).body(uploadImage);

    }

    @GetMapping("fileSystem/{fileName}")
    public ResponseEntity<?> downloadImageToFileSystem(@PathVariable String fileName) throws IOException {
        Optional<FileData> dbImageData = fileDataRepository.findByName(fileName);
        byte[] fileData = service.downloadImageFromFileSystem(fileName);
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.valueOf(dbImageData.get().getType()))
                .body(fileData);
    }
}
