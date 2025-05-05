package com.bill.tech.util;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.bill.tech.entity.Image;
import com.bill.tech.exception.CRMException;
import com.bill.tech.exception.FileNotValidException;
import com.bill.tech.repository.ImageRepository;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;

@Service
public class MediaServiceForImageAndVideoes {

	@Autowired
	private Cloudinary cloudinary;

	@Autowired
	private ImageRepository imageRepository;
	private static final Map<String, List<String>> FILE_TYPE_GROUPS = Map.of("IMAGE",
			Arrays.asList("image/jpeg", "image/png", "image/jpg"), "VIDEO",
			Arrays.asList("video/mp4", "video/x-matroska", "video/webm", "video/avi"));

	public Image uploadImage(MultipartFile file, String fileTypeGroup) {
		if (file.isEmpty()) {
			throw new CRMException(new FileNotValidException("The file is empty. Please upload a valid file."));
		}

		if (!isValidFileType(file.getContentType(), fileTypeGroup)) {
			throw new CRMException(new FileNotValidException("Invalid file type. Allowed types for " + fileTypeGroup
					+ ": " + String.join(", ", FILE_TYPE_GROUPS.get(fileTypeGroup))));
		}
		try {
			String resourceType = "auto";

			Map uploadResult = cloudinary.uploader().upload(file.getBytes(),
					ObjectUtils.asMap("resource_type", resourceType));

			String url = (String) uploadResult.get("secure_url");
			String publicId = (String) uploadResult.get("public_id");

			Image image = Image.builder().url(url).publicId(publicId).name(file.getOriginalFilename()).build();

			return imageRepository.save(image);
		} catch (IOException e) {
			throw new RuntimeException("Upload failed", e);
		}
	}

	public List<Image> getAllImages() {
		return imageRepository.findAll();
	}

	public Optional<Image> getImage(Long id) {
		return imageRepository.findById(id);
	}
	
	public void deleteImage(Long id) {
		
		Image image = imageRepository.findById(id).orElseThrow(() -> new RuntimeException("Image not found"));
		
		try {
			cloudinary.uploader().destroy(image.getPublicId(), ObjectUtils.emptyMap());
		
		} catch (IOException e) {
			throw new RuntimeException("Delete failed", e);
		}
	
	}

	public Image updateImage(Long id, MultipartFile file, String fileType) {
		//imageRepository.deleteById(id);
	deleteImage(id);
		return uploadImage(file, fileType);
	}

	private static boolean isValidFileType(String contentType, String fileTypeGroup) {
		List<String> allowedTypes = FILE_TYPE_GROUPS.get(fileTypeGroup.toUpperCase());
		return contentType != null && allowedTypes != null && allowedTypes.contains(contentType);
	}
}
