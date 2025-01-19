package com.bill.tech.util;


import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;

@Service
public class AmazonBucketService {

    private final AmazonS3 amazonS3;
    private final String bucketName;

    @Autowired
    public AmazonBucketService(AmazonS3 amazonS3, @Value("${application.bucket.name}") String bucketName) {
        this.amazonS3 = amazonS3;
        this.bucketName = bucketName;
    }

    public String uploadFile(MultipartFile file) throws IOException {
        String fileName = System.currentTimeMillis() + "-" + file.getOriginalFilename();
        amazonS3.putObject(new PutObjectRequest(bucketName, fileName, file.getInputStream(), null));

        return amazonS3.getUrl(bucketName, fileName).toString();
    }

    public byte[] downloadFile(String fileName) throws IOException {
        S3Object s3Object = amazonS3.getObject(bucketName, fileName);
        S3ObjectInputStream inputStream = s3Object.getObjectContent();
        return inputStream.readAllBytes();
    }

    public String deleteFile(String fileName) {
        amazonS3.deleteObject(bucketName, fileName);
        return "File deleted successfully!";
    }
}
