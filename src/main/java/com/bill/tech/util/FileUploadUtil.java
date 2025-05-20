package com.bill.tech.util;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import com.bill.tech.entity.Document;
import com.bill.tech.exception.CRMException;
import com.bill.tech.exception.FileNotValidException;

public class FileUploadUtil {

	// Predefined MIME types for different categories
	private static final Map<String, List<String>> FILE_TYPE_GROUPS = Map.of("IMAGE",
			Arrays.asList("image/jpeg", "image/png", "image/jpg"), "PDF", Arrays.asList("application/pdf"), "EXCEL",
			Arrays.asList("application/vnd.ms-excel",
					"application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"),
			"IMAGE/PDF", Arrays.asList("image/jpeg", "image/png", "image/jpg","application/pdf"));

	/**
	 * Uploads a file after validating its type.
	 *
	 * @param file          The file to be uploaded.
	 * @param id            Associated user or entity ID.
	 * @param fileTypeGroup The file type group (e.g., IMAGE, PDF, EXCEL).
	 * @return Document object to save in the database.
	 * @throws IOException If the file cannot be read.
	 */
	public static Document uploadFile(MultipartFile file, String fileTypeGroup, String documentCustomName)
			throws IOException {
		if (file.isEmpty()) {
			throw new CRMException(new FileNotValidException("The file is empty. Please upload a valid file."));
		}

		if (!isValidFileType(file.getContentType(), fileTypeGroup)) {
			throw new CRMException(new FileNotValidException("Invalid file type. Allowed types for " + fileTypeGroup
					+ ": " + String.join(", ", FILE_TYPE_GROUPS.get(fileTypeGroup))));
		}

		// Create and return the Document object
		Document document = new Document();

		document.setData(file.getBytes());
		document.setFileName(file.getOriginalFilename());
		document.setFileType(file.getContentType());
		document.setDocumentType(documentCustomName);

		return document;
	}

	/**
	 * Validates the file type based on the specified group.
	 *
	 * @param contentType   MIME type of the file.
	 * @param fileTypeGroup Group of allowed file types (e.g., IMAGE, PDF, EXCEL).
	 * @return True if the file type is valid; false otherwise.
	 */
	private static boolean isValidFileType(String contentType, String fileTypeGroup) {
		List<String> allowedTypes = FILE_TYPE_GROUPS.get(fileTypeGroup.toUpperCase());
		return contentType != null && allowedTypes != null && allowedTypes.contains(contentType);
	}
	
	 public static ResponseEntity<?> buildDocumentResponse(Document document) {
	        if (document != null) {
	            byte[] documentBytes = document.getData();
	            HttpHeaders headers = new HttpHeaders();
	            headers.setContentType(MediaType.parseMediaType(document.getFileType()));
	            headers.setContentDisposition(ContentDisposition.inline()
	                .filename(document.getFileName())
	                .build());

	            return new ResponseEntity<>(documentBytes, headers, HttpStatus.OK);
	        } else {
	            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	        }
	 }
}
