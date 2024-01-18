package com.itbank.simpleboard.component;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Component
@Slf4j
public class FileComponent {
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
    private String saveDirectory = "C:\\upload";

    public FileComponent() {
        File dir = new File(saveDirectory);
        if(!dir.exists()) {
            dir.mkdirs();
        }
    }

    public String upload(MultipartFile upload) {
        String fileName = sdf.format(new Date()) + "_" + upload.getOriginalFilename();
        File dest = new File(saveDirectory, fileName);
        log.info("fileuploadfileuploadfileupload");
        try {
            upload.transferTo(dest);
            return fileName;
        } catch (IllegalStateException | IOException e) {
            e.printStackTrace();
        }
        return "default.png";
    }

    public void deleteFile(String fileName) {
        File dest = new File(saveDirectory, fileName);
        if(dest.exists()) {
            dest.delete();
        }
    }
}
