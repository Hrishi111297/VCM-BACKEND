package com.bill.tech.service;

import java.io.IOException;
import java.util.EnumMap;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import com.bill.tech.enums.ApiResponseEnum;
import com.bill.tech.payload.request.CourseDto;

public interface CourseService {

	ResponseEntity<EnumMap<ApiResponseEnum, Object>> createCourse(MultipartFile image, CourseDto courseDto) throws IOException;

	ResponseEntity<EnumMap<ApiResponseEnum, Object>> updateCourse(Long id, CourseDto courseDto, MultipartFile image) throws IOException;

	ResponseEntity<EnumMap<ApiResponseEnum, Object>> deleteCourse(Long id);

	ResponseEntity<EnumMap<ApiResponseEnum, Object>> getCourse(Long id);

	ResponseEntity<EnumMap<ApiResponseEnum, Object>> getAllCourses();

	ResponseEntity<EnumMap<ApiResponseEnum, Object>> getCoursesByCategory(Long categoryId);

	ResponseEntity<EnumMap<ApiResponseEnum, Object>> getCoursesByStatus(String status);




}
