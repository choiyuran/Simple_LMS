package com.itbank.simpleboard.controller;

import org.apache.commons.io.FilenameUtils;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Controller
public class FileController {

    private final Path fileStorageLocation;

    public FileController() {
        this.fileStorageLocation = Paths.get("C:/simpleLMS/upload/syllabus"); // 파일이 저장된 경로로 수정
    }

    @GetMapping("/download/{fileName:.+}")  // :는 separator, .은 어떤 문자 하나, +는 최소 하나 이상의 문자가 나와야 한다는 뜻
    // :.+ = 마침표로 시작하는 어떤 문자열도 포함한다는 의미
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileName) throws IOException {
        Path filePath = fileStorageLocation.resolve(fileName).normalize();

        // 파일 존재 여부 확인
        if (!Files.exists(filePath)) {
            throw new FileNotFoundException("File not found: " + fileName);
        }

        Resource resource = new UrlResource(filePath.toUri());

        // 파일 확장자를 확인하여 MIME 타입 설정
        String contentType = determineContentType(fileName);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType(contentType));

        // Content-Disposition 헤더 설정
        headers.setContentDispositionFormData("attachment", URLEncoder.encode(fileName, StandardCharsets.UTF_8));

        return ResponseEntity.ok()
                .headers(headers)
                .body(resource);
    }

    private String determineContentType(String fileName) {
        // 파일 확장자 추출
        String fileExtension = FilenameUtils.getExtension(fileName.toLowerCase());

        // MIME 타입 결정
        switch (fileExtension) {
            case "pdf":
                return "application/pdf";
            case "docx":
                return "application/vnd.openxmlformats-officedocument.wordprocessingml.document";
            case "hwp":
                return "application/x-hwp";
            // 여러 확장자에 대한 처리 추가 가능
            default:
                return "application/octet-stream"; // 기본적으로 이진 데이터로 처리
        }
    }
}
