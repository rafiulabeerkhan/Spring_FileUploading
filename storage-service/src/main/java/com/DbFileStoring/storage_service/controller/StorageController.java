package com.DbFileStoring.storage_service.controller;

import com.DbFileStoring.storage_service.entity.ImageData;
import com.DbFileStoring.storage_service.repository.StorageRepository;
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
    private StorageRepository storageRepository;

    @PostMapping(value = "/upload")
    public ResponseEntity<?> uploadImage(@RequestParam("image")MultipartFile file) throws IOException {
        String uploadImage = service.uploadImage(file);
        return ResponseEntity.status(HttpStatus.OK).body(uploadImage);

    }

    @GetMapping("download/{fileName}")
  public ResponseEntity<?> downloadImage(@PathVariable String fileName){
        Optional<ImageData> dbImageData = storageRepository.findByName(fileName);
        byte[] imageData = service.downloadImage(fileName);
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.valueOf(dbImageData.get().getType()))
                .body(imageData);
  }
}
