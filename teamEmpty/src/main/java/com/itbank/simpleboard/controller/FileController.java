package com.itbank.simpleboard.controller;

import com.itbank.simpleboard.dto.ManagerLoginDto;
import com.itbank.simpleboard.dto.ProfessorDto;
import com.itbank.simpleboard.service.FileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpSession;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Controller
@Slf4j
public class FileController {

    private final FileService fileService;

    public FileController(FileService fileService) {
        this.fileService = fileService;
    }

    @GetMapping("/download/{fileName:.+}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileName) {
        String saveDir = "syllabus";
        Resource resource = fileService.loadAsResource(fileName, saveDir);

        // 파일 확장자를 확인하여 MIME 타입 설정
        String contentType = determineContentType(fileName);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType(contentType));

        // Content-Disposition 헤더 설정
        fileName = URLDecoder.decode(fileName, StandardCharsets.UTF_8);
        headers.setContentDispositionFormData("attachment", fileName);

        return ResponseEntity.ok()
                .headers(headers)
                .body(resource);
    }

    private String determineContentType(String fileName) {
        // 파일 확장자 추출
        String fileExtension = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();

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

    @GetMapping("/images/{filename:.+}")
    public ResponseEntity<Resource> serveFile(@PathVariable String filename, HttpSession session) {
        String saveDir = "";
        Object user = session.getAttribute("user");
        if (user instanceof ProfessorDto) {
            saveDir = "idPhoto_professor";
        }
        if (user instanceof ManagerLoginDto) {
            saveDir = "idPhoto_manager";
        }

        Resource file = fileService.loadAsResource(filename, saveDir);

        filename = URLEncoder.encode(filename, StandardCharsets.UTF_8);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + filename + "\"")
                .body(file);
    }
}
