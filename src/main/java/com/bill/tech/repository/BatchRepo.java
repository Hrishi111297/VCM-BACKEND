package com.bill.tech.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bill.tech.entity.Batch;
import com.bill.tech.entity.Course;

public interface BatchRepo extends JpaRepository<Batch, Long>{
	List<Batch> findByCourseId(Long categoryId);

	 List<Batch> findByIsOpen(boolean isOpen);
}
