package com.bill.tech.service;

import java.io.IOException;
import java.util.EnumMap;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import com.bill.tech.enums.ApiResponse;
import com.bill.tech.payload.request.CourseDto;

public interface CourseService {

	ResponseEntity<EnumMap<ApiResponse, Object>> createCourse(MultipartFile image, CourseDto courseDto) throws IOException;

	ResponseEntity<EnumMap<ApiResponse, Object>> updateCourse(Long id, CourseDto courseDto, MultipartFile image) throws IOException;

	ResponseEntity<EnumMap<ApiResponse, Object>> deleteCourse(Long id);

	ResponseEntity<EnumMap<ApiResponse, Object>> getCourse(Long id);

	ResponseEntity<EnumMap<ApiResponse, Object>> getAllCourses();

	ResponseEntity<EnumMap<ApiResponse, Object>> getCoursesByCategory(Long categoryId);

	ResponseEntity<EnumMap<ApiResponse, Object>> getCoursesByStatus(String status);




}
