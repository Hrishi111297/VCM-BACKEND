package com.bill.tech.controller;

import static com.bill.tech.constants.ApiConstants.COURSE;
import static com.bill.tech.constants.ApiConstants.CREATE_COURSE;
import static com.bill.tech.constants.ApiConstants.DELETE_COURSE;
import static com.bill.tech.constants.ApiConstants.GET_ALL_COURSES;
import static com.bill.tech.constants.ApiConstants.GET_COURSE;
import static com.bill.tech.constants.ApiConstants.GET_COURSES_BY_CATEGORY;
import static com.bill.tech.constants.ApiConstants.GET_COURSES_BY_STATUS;
import static com.bill.tech.constants.ApiConstants.UPDATE_COURSE;

import java.io.IOException;
import java.util.EnumMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.bill.tech.enums.ApiResponseEnum;
import com.bill.tech.payload.request.CourseDto;
import com.bill.tech.service.CourseService;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;

@RestController

@RequiredArgsConstructor
@RequestMapping(COURSE)
public class CourseController{
	@Autowired
	private ObjectMapper objectMapper;
	private final CourseService courseService;

	@PostMapping(CREATE_COURSE)
	public ResponseEntity<EnumMap<ApiResponseEnum, Object>> createCourse(@RequestParam("course") String data,
			@RequestParam("image") MultipartFile image) throws IOException {
		return courseService.createCourse(image, objectMapper.readValue(data, CourseDto.class));
	}

	@PutMapping(UPDATE_COURSE)
	public ResponseEntity<EnumMap<ApiResponseEnum, Object>> updateCourse(@PathVariable Long id,
			@RequestParam("course") String data,
			@RequestParam("image") MultipartFile image) throws IOException {
		return courseService.updateCourse(id,  objectMapper.readValue(data, CourseDto.class), image);
	}

	@DeleteMapping(DELETE_COURSE)
	public ResponseEntity<EnumMap<ApiResponseEnum, Object>> deleteCourse(@PathVariable Long id) {
		return courseService.deleteCourse(id);
	}

	@GetMapping(GET_COURSE)
	public ResponseEntity<EnumMap<ApiResponseEnum, Object>> getCourse(@PathVariable Long id) {
		return courseService.getCourse(id);
	}

	@GetMapping(GET_ALL_COURSES)
	public ResponseEntity<EnumMap<ApiResponseEnum, Object>> getAllCourses() {
		return courseService.getAllCourses();
	}

	@GetMapping(GET_COURSES_BY_CATEGORY)
	public ResponseEntity<EnumMap<ApiResponseEnum, Object>> getCoursesByCategory(@PathVariable Long categoryId) {
		return courseService.getCoursesByCategory(categoryId);
	}

	@GetMapping(GET_COURSES_BY_STATUS)
	public ResponseEntity<EnumMap<ApiResponseEnum, Object>> getCoursesByStatus(@PathVariable String status) {
		return courseService.getCoursesByStatus(status);
	}
}
