package com.DbFileStoring.storage_service.service;

import com.DbFileStoring.storage_service.entity.ImageData;
import com.DbFileStoring.storage_service.repository.StorageRepository;
import com.DbFileStoring.storage_service.util.ImageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;
import java.util.Optional;

@Service
public class StorageService {

    @Autowired
    public StorageRepository repository;

    public String uploadImage(MultipartFile file) throws IOException {
      ImageData imageData = repository.save(
                ImageData.builder()
                        .name(file.getOriginalFilename())
                        .type(file.getContentType())
                        .imageInfo(Base64.getEncoder().encodeToString(ImageUtils.compressImage(file.getBytes())))
                        .build());
                       if(imageData != null){
                           return "File Uploaded Successfully " + file.getOriginalFilename();
                       }
                          return null;
    }

    public byte[] downloadImage(String fileName){
        Optional<ImageData> dbImageData = repository.findByName(fileName);
        byte[] images = ImageUtils.decompressImage(Base64.getDecoder().decode(dbImageData.get().getImageInfo()));
        return images;
    }

}
