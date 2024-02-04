package com.itbank.simpleboard.service;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;

import java.net.MalformedURLException;
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
            Path filePath = fileStorageLocation.resolve(Paths.get(saveDir, filename)).normalize(); // 여기서 resolve 안에 사진이나 파일 저장 되는 폴더 이름 넣어야 함(idPhoto_professor, syllabus)
            Resource resource = new UrlResource(filePath.toUri());

            if (resource.exists()) {
                return resource;
            } else {
                throw new RuntimeException("File not found: " + filename);
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException("File found Error: " + filename, e);
        }
    }
}
