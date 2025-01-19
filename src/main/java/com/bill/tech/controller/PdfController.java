package com.bill.tech.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import com.bill.tech.entity.PdfDocument;
import com.bill.tech.repository.PdfDocumentRepository;

@RestController

@RequestMapping("/auth")
public class PdfController {

    @Autowired
    private PdfDocumentRepository pdfDocumentRepository;

    @GetMapping("/{id}")
    public ResponseEntity<byte[]> getPdfById(@PathVariable Long id) {
        PdfDocument pdfDocument = pdfDocumentRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "PDF not found"));

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDisposition(ContentDisposition.inline().filename(pdfDocument.getName()).build());

        return new ResponseEntity<>(pdfDocument.getContent(), headers, HttpStatus.OK);
    }

    @PostMapping("/upload")
    public ResponseEntity<String> uploadPdf(@RequestParam("file") MultipartFile file) {
        try {
            // Check file type
            if (!file.getContentType().equals(MediaType.APPLICATION_PDF_VALUE)) {
                return ResponseEntity.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
                        .body("Only PDF files are allowed.");
            }

            PdfDocument pdfDocument = new PdfDocument();
            pdfDocument.setName(file.getOriginalFilename());
            pdfDocument.setContent(file.getBytes());

            pdfDocumentRepository.save(pdfDocument);

            return ResponseEntity.status(HttpStatus.CREATED)
                    .body("PDF uploaded successfully with ID: " + pdfDocument.getId());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while uploading the PDF: " + e.getMessage());
        }
    }
}
