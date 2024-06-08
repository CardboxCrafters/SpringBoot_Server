package com.mycompany.myapp.service;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface S3Service {
    List<String> uploadFiles(List<MultipartFile> multipartFiles);
    String uploadFile(MultipartFile file);
    String uploadFile(byte[] fileBytes, String fileName);
    void deleteFile(String fileUrl);
}
