package com.DbFileStoring.storage_service.service;

import com.DbFileStoring.storage_service.entity.FileData;
import com.DbFileStoring.storage_service.repository.FileDataRepository;
import com.DbFileStoring.storage_service.util.ImageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Base64;
import java.util.Optional;

@Service
public class StorageService {


    @Autowired
    public FileDataRepository fileDataRepository;

    private final String FOLDER_PATH = "G:\\MyFiles";

    public String uploadImageToFileSystem(MultipartFile file) throws IOException {
        String file_path = FOLDER_PATH+file.getOriginalFilename();

        FileData fileData = fileDataRepository.save(FileData.builder()
                        .name(file.getOriginalFilename())
                        .type(file.getContentType())
                        .filePath(file_path)
                        .build());

        file.transferTo(new File(file_path));

        if(fileData != null){
            return "File Uploaded Successfully " + fileData;
        }
        return null;
    }

    public byte[] downloadImageFromFileSystem(String fileName) throws IOException {
        Optional<FileData> fileData = fileDataRepository.findByName(fileName);
        String filePath=fileData.get().getFilePath();
        byte[] images = Files.readAllBytes(new File(filePath).toPath());
        return images;
    }

}
