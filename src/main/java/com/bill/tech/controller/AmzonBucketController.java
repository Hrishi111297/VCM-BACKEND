package com.bill.tech.controller;
import static com.bill.tech.constants.ApiConstants.AWS_BUCKET;
import static com.bill.tech.constants.ApiConstants.AWS_LIST;
import static com.bill.tech.constants.ApiConstants.DELETE_ALL;
import static com.bill.tech.constants.ApiConstants.DELETE_FILENAME;
import static com.bill.tech.constants.ApiConstants.DOWNLOAD;
import static com.bill.tech.constants.ApiConstants.UPLOAD;

import java.io.IOException;
import java.util.List;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.bill.tech.util.AmazonBucketService;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
/**
 * @author Hrishikesh Mahadik
 * @since 11/01/2025
 * @version 1.0
 *
 */
@RequestMapping(AWS_BUCKET)
@Tag(name = "AWS_BUCKET", description = "This Section Gives Us The API Endpoint Related To The AWS_BUCKET")

@RequiredArgsConstructor
@RestController
public class AmzonBucketController {

    private final AmazonBucketService amazonBucketService;

    @PostMapping(UPLOAD)
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) throws IOException {
        String fileUrl = amazonBucketService.uploadFile(file);
        return ResponseEntity.ok("File uploaded successfully! File URL: " + fileUrl);
    }

    /**
     * Download a file from the S3 bucket.
     */
    @GetMapping(DOWNLOAD)
    public ResponseEntity<ByteArrayResource> downloadFile(@PathVariable String fileName) throws IOException {
        byte[] fileData = amazonBucketService.downloadFile(fileName);
        ByteArrayResource resource = new ByteArrayResource(fileData);

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"")
                .body(resource);
    }

    /**
     * List all files in the S3 bucket.
     */
    @GetMapping(AWS_LIST)
    public ResponseEntity<List<String>> listAllFiles() {
        List<String> files = amazonBucketService.listAllFiles();
        return ResponseEntity.ok(files);
    }

    /**
     * Delete a single file from the S3 bucket.
     */
    @DeleteMapping(DELETE_FILENAME)
    public ResponseEntity<String> deleteFile(@PathVariable String fileName) {
        String response = amazonBucketService.deleteFile(fileName);
        return ResponseEntity.ok(response);
    }

    /**
     * Delete all files from the S3 bucket.
     */
    @DeleteMapping(DELETE_ALL)
    public ResponseEntity<String> deleteAllFiles() {
        String response = amazonBucketService.deleteAllFiles();
        return ResponseEntity.ok(response);
    }
}







