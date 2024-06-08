package com.mycompany.myapp.service.impl;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import lombok.RequiredArgsConstructor;
import com.mycompany.myapp.service.S3Service;

@Service
@RequiredArgsConstructor
public class S3ServiceImpl implements S3Service {

    private final AmazonS3 amazonS3;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    @Override
    public List<String> uploadFiles(List<MultipartFile> multipartFiles) {
        List<String> fileUrls = new ArrayList<>();
        for (MultipartFile file : multipartFiles) {
            String fileName = createFileName(file.getOriginalFilename());
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentLength(file.getSize());
            metadata.setContentType(file.getContentType());

            try (InputStream inputStream = file.getInputStream()) {
                amazonS3.putObject(new PutObjectRequest(bucket, fileName, inputStream, metadata)
                        .withCannedAcl(CannedAccessControlList.PublicRead));
                fileUrls.add(amazonS3.getUrl(bucket, fileName).toString());
            } catch (IOException e) {
                throw new RuntimeException("Failed to upload files", e);
            }
        }
        return fileUrls;
    }

    @Override
    public String uploadFile(MultipartFile file) {
        String fileName = createFileName(file.getOriginalFilename());
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(file.getSize());
        metadata.setContentType(file.getContentType());

        try (InputStream inputStream = file.getInputStream()) {
            amazonS3.putObject(new PutObjectRequest(bucket, fileName, inputStream, metadata)
                    .withCannedAcl(CannedAccessControlList.PublicRead));
            return amazonS3.getUrl(bucket, fileName).toString();
        } catch (IOException e) {
            throw new RuntimeException("Failed to upload file", e);
        }
    }

    @Override
    public String uploadFile(byte[] fileBytes, String fileName) {
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(fileBytes.length);
        metadata.setContentType("image/jpeg"); // 필요한 경우 적절한 콘텐츠 유형으로 설정

        try (InputStream inputStream = new ByteArrayInputStream(fileBytes)) {
            amazonS3.putObject(new PutObjectRequest(bucket, fileName, inputStream, metadata)
                    .withCannedAcl(CannedAccessControlList.PublicRead));
            return amazonS3.getUrl(bucket, fileName).toString();
        } catch (IOException e) {
            throw new RuntimeException("Failed to upload file", e);
        }
    }

    private String createFileName(String originalFilename) {
        return UUID.randomUUID().toString() + "_" + originalFilename;
    }

    @Override
    public void deleteFile(String fileUrl) {
        try {
            String fileName = fileUrl.substring(fileUrl.lastIndexOf("/") + 1);
            amazonS3.deleteObject(bucket, fileName);
        } catch (Exception e) {
            throw new RuntimeException("Failed to delete file", e);
        }
    }
}
