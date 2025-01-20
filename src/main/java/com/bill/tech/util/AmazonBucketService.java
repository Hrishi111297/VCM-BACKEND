//package com.bill.tech.util;
//
//import java.io.IOException;
//import java.util.List;
//import java.util.Objects;
//import java.util.stream.Collectors;
//
//import org.springframework.stereotype.Service;
//import org.springframework.web.multipart.MultipartFile;
//
//import com.amazonaws.services.s3.AmazonS3;
//import com.amazonaws.services.s3.model.DeleteObjectsRequest;
//import com.amazonaws.services.s3.model.DeleteObjectsRequest.KeyVersion;
//import com.amazonaws.services.s3.model.ListObjectsV2Request;
//import com.amazonaws.services.s3.model.ListObjectsV2Result;
//import com.amazonaws.services.s3.model.ObjectMetadata;
//import com.amazonaws.services.s3.model.PutObjectRequest;
//import com.amazonaws.services.s3.model.S3Object;
//import com.amazonaws.services.s3.model.S3ObjectInputStream;
//
//import lombok.RequiredArgsConstructor;
//
//@Service
//@RequiredArgsConstructor
//public class AmazonBucketService {
//
//	private final AmazonS3 amazonS3;
//	private final String bucketName;
//
//	/**
//	 * Uploads a file to S3 bucket with content length metadata.
//	 */
//	public String uploadFile(MultipartFile file) throws IOException {
//		String fileName = System.currentTimeMillis() + "-" + file.getOriginalFilename();
//
//		// Set metadata for the uploaded file
//		ObjectMetadata metadata = new ObjectMetadata();
//		metadata.setContentLength(file.getSize());
//		metadata.setContentType(file.getContentType());
//
//		amazonS3.putObject(new PutObjectRequest(bucketName, fileName, file.getInputStream(), metadata));
//		return amazonS3.getUrl(bucketName, fileName).toString();
//	}
//
//	/**
//	 * Downloads a file from S3 bucket.
//	 */
//	public byte[] downloadFile(String fileName) throws IOException {
//		S3Object s3Object = amazonS3.getObject(bucketName, fileName);
//		S3ObjectInputStream inputStream = s3Object.getObjectContent();
//		return inputStream.readAllBytes();
//	}
//
//	/**
//	 * Deletes a single file from S3 bucket.
//	 */
//	public String deleteFile(String fileName) {
//		amazonS3.deleteObject(bucketName, fileName);
//		return "File deleted successfully!";
//	}
//
//	/**
//	 * Retrieves all file names in the S3 bucket.
//	 */
//	public List<String> listAllFiles() {
//		ListObjectsV2Request request = new ListObjectsV2Request().withBucketName(bucketName);
//		ListObjectsV2Result result = amazonS3.listObjectsV2(request);
//
//		return result.getObjectSummaries().stream().map(summary -> summary.getKey()).collect(Collectors.toList());
//	}
//
//	/**
//	 * Deletes all files from the S3 bucket.
//	 */
//	public String deleteAllFiles() {
//		List<String> fileNames = listAllFiles();
//
//		if (fileNames.isEmpty()) {
//			return "No files to delete.";
//		}
//
//		List<KeyVersion> keys = fileNames.stream().map(KeyVersion::new).collect(Collectors.toList());
//
//		DeleteObjectsRequest deleteObjectsRequest = new DeleteObjectsRequest(bucketName).withKeys(keys);
//
//		amazonS3.deleteObjects(deleteObjectsRequest);
//
//		return "All files deleted successfully!";
//	}
//
//	public byte[] downloadFileByUrl(String fileUrl) throws IOException {
//
//		return downloadFile(getFileName(fileUrl));
//	}
//
//	public String deleteFileByUrl(String fileUrl) throws IOException {
//
//		return deleteFile(getFileName(fileUrl));
//	}
//
//	public String getFileName(String fileUrl) {
//		if (Objects.nonNull(fileUrl) && !fileUrl.isEmpty()) {
//			return fileUrl.substring(fileUrl.lastIndexOf('/') + 1);
//		}
//		return fileUrl;
//	}
//}
