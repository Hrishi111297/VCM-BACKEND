package com.bill.tech.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bill.tech.entity.Course;

public interface CourseRepo  extends JpaRepository<Course, Long>{

	List<Course> findByCategoryId(Long categoryId);

	List<Course> findByStatus(String status);

}
