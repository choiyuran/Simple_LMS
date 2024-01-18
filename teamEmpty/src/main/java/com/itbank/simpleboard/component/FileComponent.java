package com.itbank.simpleboard.component;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Component
@Slf4j
public class FileComponent {

    @Value("${file.upload-dir}")
    private String uploadDir;

    /**
     * 파일 업로드 메서드
     *
     * @param upload 업로드할 파일
     * @param directoryName 업로드할 디렉토리 이름
     * @return 업로드된 파일의 이름, 실패 시 null
     */
    public String upload(MultipartFile upload, String directoryName) {
        log.info("fileUpload");
        String dirString = getSpecificUploadDir(directoryName);

        File dir = new File(dirString);

        // 저장 디렉토리가 존재하지 않으면 생성
        if (!dir.exists()) {
            dir.mkdirs();
        }

        // 업로드된 파일의 원본 파일명 획득
        String fileName = upload.getOriginalFilename();

        // 파일명이 존재할 경우에만 처리
        if (fileName != null) {
            File dest = new File(dirString, fileName);

            try {
                // 파일을 저장
                upload.transferTo(dest);
                log.info("File uploaded to {}: {}", directoryName, fileName);
                return fileName;
            } catch (IllegalStateException | IOException e) {
                e.printStackTrace();
                // 파일 저장 실패 시 예외 처리
            }
        }
        return null;
    }

    /**
     * 파일 삭제 메서드
     *
     * @param fileName 삭제할 파일의 이름
     * @param directoryName
     */
    public void deleteFile(String fileName, String directoryName) {
        log.info("deleteFile");
        String specificUploadDir = getSpecificUploadDir(directoryName);
        File dest = new File(specificUploadDir, fileName);

        // 파일이 존재하면 삭제
        if (dest.exists()) {
            dest.delete();
            log.info("File deleted from {}: {}", directoryName, fileName);
        } else {
            log.warn("File not found in {}: {}", directoryName, fileName);
        }
    }

    /**
     * 디렉토리 이름에 따라 적절한 uploadDir을 반환하는 메서드
     */
    private String getSpecificUploadDir(String directoryName) {
        return uploadDir + File.separator + directoryName;
    }
}
