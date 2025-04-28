package com.bill.tech.service.impl;


import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.bill.tech.entity.Image;
import com.bill.tech.repository.ImageRepository;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;

@Service
public class ImageUploadServ {

    @Autowired
    private Cloudinary cloudinary;

    @Autowired
    private ImageRepository imageRepository;

    public Image uploadImage(MultipartFile file) {
        try { String contentType = file.getContentType();
        String resourceType = "auto"; 

        Map uploadResult = cloudinary.uploader().upload(
            file.getBytes(),
            ObjectUtils.asMap(
                "resource_type", resourceType
            )
        );

            String url = (String) uploadResult.get("secure_url");
            String publicId = (String) uploadResult.get("public_id");

            Image image = Image.builder()
                .url(url)
                .publicId(publicId).name(file.getOriginalFilename())
                .build();

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
        imageRepository.deleteById(id);
    }

    public Image updateImage(Long id, MultipartFile file) {
        deleteImage(id); 
        return uploadImage(file); 
    }
}
