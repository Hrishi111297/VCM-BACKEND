package com.bill.tech.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bill.tech.entity.PdfDocument;

@Repository
public interface PdfDocumentRepository extends JpaRepository<PdfDocument, Long> {
}