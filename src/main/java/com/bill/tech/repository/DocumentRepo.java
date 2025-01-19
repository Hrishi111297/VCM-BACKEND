package com.bill.tech.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bill.tech.entity.Document;
import com.bill.tech.entity.UserMaster;

public interface DocumentRepo extends JpaRepository<Document,Long> {
	 Document findByUserAndDocumentType(UserMaster user, String documentType);
}
