package com.itbank.simpleboard.component;

import com.amazonaws.services.s3.AmazonS3;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.UUID;

@Component
@Slf4j
public class FileComponent {

    @Autowired
    private final AmazonS3 amazonS3;

    @Value("${cloud.aws.s3.bucket}")
    private String s3BucketName;

    public FileComponent(AmazonS3 amazonS3) {
        this.amazonS3 = amazonS3;
    }

    /**
     * 파일 업로드 메서드
     *
     * @param upload         업로드할 파일
     * @param directoryName  업로드할 디렉토리 이름
     * @return 업로드된 파일의 URL, 실패 시 null
     */
    public String upload(MultipartFile upload, String directoryName) {
        log.info("fileUpload");

        // 파일명에서 공백을 "_"로 대체
        String originalFileName = upload.getOriginalFilename();
        String sanitizedFileName = (originalFileName != null) ? originalFileName.replaceAll(" ", "_") : UUID.randomUUID().toString();

        String key = directoryName + "/" + sanitizedFileName;

        try (InputStream inputStream = upload.getInputStream()) {
            amazonS3.putObject(s3BucketName, key, inputStream, null);

            log.info("File uploaded to S3: {}", key);
            return sanitizedFileName;
        } catch (Exception e) {
            e.printStackTrace();
            // 파일 저장 실패 시 예외 처리
        }
        return null;
    }

    public String uploadIdPhoto(MultipartFile upload, String directoryName, String newFileName) {
        log.info("fileUpload");

        // 파일명에서 공백을 "_"로 대체
        String sanitizedFileName = (newFileName != null) ? newFileName.replaceAll(" ", "_") : UUID.randomUUID().toString();

        String key = directoryName + "/" + sanitizedFileName;

        try (InputStream inputStream = upload.getInputStream()) {
            amazonS3.putObject(s3BucketName, key, inputStream, null);

            log.info("File uploaded to S3: {}", key);
            return sanitizedFileName;
        } catch (Exception e) {
            e.printStackTrace();
            // 파일 저장 실패 시 예외 처리
        }
        return null;
    }

    /**
     * 파일 삭제 메서드
     *
     * @param fileName       삭제할 파일의 이름
     * @param directoryName  디렉토리 이름
     */
    public void deleteFile(String fileName, String directoryName) {
        log.info("deleteFile");
        String key = directoryName + "/" + fileName;

        // 파일이 존재하면 삭제
        if (amazonS3.doesObjectExist(s3BucketName, key)) {
            amazonS3.deleteObject(s3BucketName, key);
            log.info("File deleted from S3: {}", key);
        } else {
            log.warn("File not found in S3: {}", key);
        }
    }
}
