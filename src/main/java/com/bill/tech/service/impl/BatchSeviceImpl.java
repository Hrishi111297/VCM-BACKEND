//package com.bill.tech.service.impl;
//
//import static com.bill.tech.constants.ApiMessages.ADDED;
//import static com.bill.tech.constants.ApiMessages.CATEGORY;
//import static com.bill.tech.constants.ApiMessages.BATCH;
//import static com.bill.tech.constants.ApiMessages.DELETED;
//import static com.bill.tech.constants.ApiMessages.FETCHED;
//import static com.bill.tech.constants.ApiMessages.FOR;
//import static com.bill.tech.constants.ApiMessages.NOT_FOUND;
//import static com.bill.tech.constants.ApiMessages.STATUS;
//import static com.bill.tech.constants.ApiMessages.*;
//import static com.bill.tech.constants.FileTypes.BATCH_BANNER;
//import static com.bill.tech.constants.FileTypes.IMAGE;
//import static com.bill.tech.dto_mapper.CourseMapper.TO_BATCH;
//import static com.bill.tech.dto_mapper.CourseMapper.TO_BATCH_DTO;
//import static com.bill.tech.dto_mapper.BatchMapper.*;
//
//import java.io.IOException;
//import java.util.EnumMap;
//import java.util.List;
//
//import org.springframework.boot.autoconfigure.batch.BatchDataSource;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.stereotype.Service;
//import org.springframework.web.multipart.MultipartFile;
//
//import com.bill.tech.entity.Batch;
//import com.bill.tech.entity.Category;
//import com.bill.tech.entity.Course;
//import com.bill.tech.entity.Document;
//import com.bill.tech.enums.ApiResponseEnum;
//import com.bill.tech.exception.ResourceNotFound;
//import com.bill.tech.payload.request.BatchDto;
//import com.bill.tech.payload.request.CourseDto;
//import com.bill.tech.payload.response.ApiResponse;
//import com.bill.tech.repository.BatchRepo;
//import com.bill.tech.repository.CategoryRepo;
//import com.bill.tech.repository.CourseRepo;
//import com.bill.tech.service.BatchService;
//import com.bill.tech.util.FileUploadUtil;
//
//import lombok.RequiredArgsConstructor;
//
//@RequiredArgsConstructor
//@Service
//public class BatchSeviceImpl implements BatchService {
//
//	private final BatchRepo batchRepo;
//	private final CategoryRepo categoryRepo;
//
//	@Override
//	public ResponseEntity<EnumMap<ApiResponseEnum, Object>> createbatch(BatchDto batchDto)
//			throws java.io.IOException {
//
//		Batch batch= TO_BATCH.apply(batchDto).orElseThrow(() -> new ResourceNotFound(BATCH));
//		batchDto.getTeacherIds().isEmpty()?ResourceNotFound(BATCH):batchDto.getTeacherIds()
//		
//		
//		
//		forEach();
//		Course savedCourse = courseRepo.save(course);
//		return ApiResponse.buildSuccesResponse(TO_BATCH_DTO.apply(savedCourse), BATCH + ADDED, HttpStatus.CREATED);
//	}
//
//	private Category getCatogoryById(CourseDto courseDto) {
//		Category category = categoryRepo.findById(courseDto.getCategoryId())
//				.orElseThrow(() -> new ResourceNotFound("Category", "Id", String.valueOf(courseDto.getCategoryId())));
//		return category;
//	}
//
//	@Override
//	public ResponseEntity<EnumMap<ApiResponseEnum, Object>> updateCourse(Long id, CourseDto courseDto,
//			MultipartFile image) throws IOException {
//
//		Course existingCourse = getCourseById(id);
//
//		Document document = FileUploadUtil.uploadFile(image, IMAGE, BATCH_BANNER);
//		courseDto.setImage(document.getData());
//		existingCourse.setName(courseDto.getName());
//		existingCourse.setDescription(courseDto.getDescription());
//		existingCourse.setFee(courseDto.getFee());
//		existingCourse.setSyllabus(courseDto.getSyllabus());
//		existingCourse.setDuration(courseDto.getDuration());
//		existingCourse.setStatus(courseDto.getStatus());
//		existingCourse.setLevel(courseDto.getLevel());
//		existingCourse.setLanguage(courseDto.getLanguage());
//		existingCourse.setStartDate(courseDto.getStartDate());
//		existingCourse.setEndDate(courseDto.getEndDate());
//		existingCourse.setImage(courseDto.getImage());
//		// amazonBucketService.deleteFileByUrl(existingCourse.getImageUrl());
//		// String imageUrl = amazonBucketService.uploadFile(image);
//		// existingCourse.setImageUrl(imageUrl);
//		Course updatedCourse = courseRepo.save(existingCourse);
//		return ApiResponse.buildSuccesResponse(TO_BATCH_DTO.apply(updatedCourse), BATCH + UPDATED, HttpStatus.OK);
//	}
//
//	private Course getCourseById(Long id) {
//		return courseRepo.findById(id).orElseThrow(() -> new ResourceNotFound("Course", "id", id.toString()));
//	}
//
//	@Override
//	public ResponseEntity<EnumMap<ApiResponseEnum, Object>> deleteCourse(Long id) {
//		Course course = getCourseById(id);
//		courseRepo.delete(course);
//		return ApiResponse.buildSuccesResponse(null, BATCH + DELETED, HttpStatus.OK);
//	}
//
//	@Override
//	public ResponseEntity<EnumMap<ApiResponseEnum, Object>> getCourse(Long id) {
//		Course course = getCourseById(id);
//
//		return ApiResponse.buildSuccesResponse(TO_BATCH_DTO.apply(course), BATCH + FETCHED, HttpStatus.OK);
//	}
//
//	@Override
//	public ResponseEntity<EnumMap<ApiResponseEnum, Object>> getAllCourses() {
//		List<Course> courses = courseRepo.findAll();
//
//		return ApiResponse.buildSuccesResponse(TO_BATCH_DTOS.apply(courses), BATCH + FETCHED, HttpStatus.OK);
//	}
//
//	@Override
//	public ResponseEntity<EnumMap<ApiResponseEnum, Object>> getCoursesByCategory(Long categoryId) {
//		List<Course> courses = courseRepo.findByCategoryId(categoryId);
//		if (courses.isEmpty()) {
//			return ApiResponse.buildfailureApiResponse1(null, BATCH + NOT_FOUND + FOR + CATEGORY,
//					HttpStatus.NOT_FOUND);
//		} else {
//			return ApiResponse.buildSuccesResponse(TO_BATCH_DTOS.apply(courses), BATCH + FETCHED, HttpStatus.OK);
//		}
//	}
//
//	@Override
//	public ResponseEntity<EnumMap<ApiResponseEnum, Object>> getCoursesByStatus(String status) {
//
//		List<Course> courses = courseRepo.findByStatus(status);
//		if (courses.isEmpty()) {
//			return ApiResponse.buildfailureApiResponse1(null, BATCH + NOT_FOUND + FOR + STATUS, HttpStatus.NOT_FOUND);
//		} else {
//			return ApiResponse.buildSuccesResponse(TO_BATCH_DTOS.apply(courses), BATCH + FETCHED, HttpStatus.OK);
//		}
//
//	}
//}
