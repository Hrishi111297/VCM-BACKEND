package com.bill.tech.controller;

import static com.bill.tech.constants.ApiConstants.BATCH;
import static com.bill.tech.constants.ApiConstants.CREATE_BATCH;
import static com.bill.tech.constants.ApiConstants.DELETE_BATCH;
import static com.bill.tech.constants.ApiConstants.GET_ALL_BATCHES;
import static com.bill.tech.constants.ApiConstants.GET_BATCH;
import static com.bill.tech.constants.ApiConstants.GET_BATCH_BY_COURSE;
import static com.bill.tech.constants.ApiConstants.GET_COURSES_BY_STATUS;
import static com.bill.tech.constants.ApiConstants.UPDATE_BATCH;

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
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.bill.tech.enums.ApiResponseEnum;
import com.bill.tech.payload.request.CourseDto;
import com.bill.tech.service.CourseService;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;
/**
 * @author Hrishikesh Mahadik
 * @since 11/01/2025
 * @version 1.0
 *
 */
@RestController
@RequiredArgsConstructor
@RequestMapping(BATCH)
public class BatchController {
	@Autowired
	private ObjectMapper objectMapper;
	private final CourseService courseService;

	@PostMapping(CREATE_BATCH)
	public ResponseEntity<EnumMap<ApiResponseEnum, Object>> createCourse(@RequestParam("course") String data,
			@RequestParam("image") MultipartFile image) throws IOException {
		return courseService.createCourse(image, objectMapper.readValue(data, CourseDto.class));
	}

	@PutMapping(UPDATE_BATCH)
	public ResponseEntity<EnumMap<ApiResponseEnum, Object>> updateCourse(@PathVariable Long id,
			@RequestParam("course") String data, @RequestParam("image") MultipartFile image) throws IOException {
		return courseService.updateCourse(id, objectMapper.readValue(data, CourseDto.class), image);
	}

	@DeleteMapping(DELETE_BATCH)
	public ResponseEntity<EnumMap<ApiResponseEnum, Object>> deleteCourse(@PathVariable Long id) {
		return courseService.deleteCourse(id);
	}

	@GetMapping(GET_BATCH)
	public ResponseEntity<EnumMap<ApiResponseEnum, Object>> getCourse(@PathVariable Long id) {
		return courseService.getCourse(id);
	}

	@GetMapping(GET_ALL_BATCHES)
	public ResponseEntity<EnumMap<ApiResponseEnum, Object>> getAllCourses() {
		return courseService.getAllCourses();
	}

	@GetMapping(GET_BATCH_BY_COURSE)
	public ResponseEntity<EnumMap<ApiResponseEnum, Object>> getCoursesByCategory(@PathVariable Long categoryId) {
		return courseService.getCoursesByCategory(categoryId);
	}

	@GetMapping(GET_COURSES_BY_STATUS)
	public ResponseEntity<EnumMap<ApiResponseEnum, Object>> getCoursesByStatus(@PathVariable String status) {
		return courseService.getCoursesByStatus(status);
	}
}
