package com.JPA.JPA.Helper;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Component
public class FileUploader {
    private final String path = "/Users/abhishektanwar/IdeaProjects/JPA/src/main/resources/static/uploads";

    public FileUploader() throws IOException {
    }

    public boolean upload(MultipartFile file) {
        boolean fileUploaded = false;
        try {
            Files.copy(file.getInputStream(), Paths.get(path + File.separator+file.getOriginalFilename()),
                    StandardCopyOption.REPLACE_EXISTING);
            fileUploaded = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return fileUploaded;
    }
}
