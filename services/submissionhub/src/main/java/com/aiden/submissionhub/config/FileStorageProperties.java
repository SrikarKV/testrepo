package com.aiden.submissionhub.config;

import org.springframework.stereotype.Component;

import java.io.File;

@Component
public class FileStorageProperties {
    private String uploadDir;

    public FileStorageProperties() {
        this.uploadDir = System.getProperty("user.dir") + "/Uploads" + File.separator;
    }

    public String getUploadDir() {
        return uploadDir;
    }

    public void setUploadDir(String uploadDir) {
        this.uploadDir = uploadDir;
    }
}
