// FileService.java
package com.itbank.simpleboard.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.S3Object;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

@Service
public class FileService {

    private final AmazonS3 amazonS3;
    private final String s3BucketName = "simplelmsbucket"; // AWS S3 버킷 이름으로 변경

    public FileService(AmazonS3 amazonS3) {
        this.amazonS3 = amazonS3;
    }

    public Resource loadAsResource(String fileName, String saveDir) {
        try {
            String key = getPath(saveDir, fileName);

            // URL 디코딩
            String decodedFileName = URLDecoder.decode(key, StandardCharsets.UTF_8.toString());

            S3Object s3Object = amazonS3.getObject(s3BucketName, decodedFileName);
            return new InputStreamResource(s3Object.getObjectContent());
        } catch (Exception e) {
            // 예외 처리 로직 추가
            throw new RuntimeException("Error accessing file: " + fileName, e);
        }
    }

    private String getPath(String saveDir, String filename) {
        return saveDir + "/" + filename;
    }
}
