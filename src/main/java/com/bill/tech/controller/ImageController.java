package com.bill.tech.controller;


import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.bill.tech.entity.Image;
import com.bill.tech.service.impl.ImageUploadServ;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/images/")
public class ImageController {
private  final ImageUploadServ  imageUploadServ;

@PostMapping("/upload")
public Image uploadImage(@RequestParam("file")MultipartFile file){
	return imageUploadServ.uploadImage(file);

}
@GetMapping
public ResponseEntity<List<Image>> getAllImages() {
    return ResponseEntity.ok(imageUploadServ.getAllImages());
}

@GetMapping("/{id}")
public ResponseEntity<Image> getImage(@PathVariable Long id) {
    return imageUploadServ.getImage(id)
        .map(ResponseEntity::ok)
        .orElse(ResponseEntity.notFound().build());
}

@DeleteMapping("/{id}")
public ResponseEntity<Void> deleteImage(@PathVariable Long id) {
    imageUploadServ.deleteImage(id);
    return ResponseEntity.noContent().build();
}

@PutMapping("/{id}")
public ResponseEntity<Image> updateImage(@PathVariable Long id, @RequestParam("file") MultipartFile file) {
    return ResponseEntity.ok(imageUploadServ.updateImage(id, file));
}

}
