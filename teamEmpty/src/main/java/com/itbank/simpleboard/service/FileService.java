package com.itbank.simpleboard.service;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;

import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class FileService {
    private final Path fileStorageLocation;

    public FileService() {
        this.fileStorageLocation = Paths.get("C:/simpleLMS/upload");
    }

    public Resource loadAsResource(String filename, String saveDir) {
        try {
            Path filePath = getFileStorageLocation(saveDir).resolve(filename).normalize();
            Resource resource = new UrlResource(filePath.toUri());

            if (resource.exists() && resource.isReadable()) {
                return resource;
            } else {
                throw new RuntimeException("File not found or cannot be read: " + filename);
            }
        } catch (Exception e) {
            throw new RuntimeException("Error accessing file: " + filename, e);
        }
    }

    private Path getFileStorageLocation(String saveDir) {
        return fileStorageLocation.resolve(saveDir);
    }
}