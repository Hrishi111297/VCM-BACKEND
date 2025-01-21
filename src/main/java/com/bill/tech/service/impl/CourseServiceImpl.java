package com.bill.tech.service.impl;

import static com.bill.tech.constants.FileTypes.COURSE_BANNER;
import static com.bill.tech.constants.FileTypes.IMAGE;
import static com.bill.tech.dto_mapper.CourseMapper.TO_COURSE;
import static com.bill.tech.dto_mapper.CourseMapper.TO_COURSE_DTO;
import static com.bill.tech.dto_mapper.CourseMapper.TO_COURSE_DTOS;
import static com.bill.tech.enums.ApiResponseEnum.DATA;
import static com.bill.tech.enums.ApiResponseEnum.MESSAGE;
import static com.bill.tech.enums.ApiResponseEnum.SUCCESS;

import java.io.IOException;
import java.util.EnumMap;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.bill.tech.entity.Category;
import com.bill.tech.entity.Course;
import com.bill.tech.entity.Document;
import com.bill.tech.enums.ApiResponseEnum;
import com.bill.tech.exception.ResourceNotFound;
import com.bill.tech.payload.request.CourseDto;
import com.bill.tech.repository.CategoryRepo;
import com.bill.tech.repository.CourseRepo;
import com.bill.tech.service.CourseService;
import com.bill.tech.util.FileUploadUtil;

import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
@Service
public class CourseServiceImpl implements CourseService {

	private final CourseRepo courseRepo;
	private final CategoryRepo categoryRepo;
//	private final AmazonBucketService amazonBucketService;

	@Override
	public ResponseEntity<EnumMap<ApiResponseEnum, Object>> createCourse(MultipartFile image, CourseDto courseDto)
			throws java.io.IOException {
		EnumMap<ApiResponseEnum, Object> responseMap = new EnumMap<>(ApiResponseEnum.class);

		Document document = FileUploadUtil.uploadFile(image, IMAGE, COURSE_BANNER);
		courseDto.setImage(document.getData());
		
	//	  String imageUrl = amazonBucketService.uploadFile(image);
	//	  courseDto.setImageUrl(imageUrl);
		Course course = TO_COURSE.apply(courseDto).orElseThrow(() -> new ResourceNotFound("Course"));
		Category category = categoryRepo.findById(courseDto.getCategoryId())
				.orElseThrow(() -> new ResourceNotFound("Category", "Id", String.valueOf(courseDto.getCategoryId())));
		course.setCategory(category);
		Course savedCourse = courseRepo.save(course);

		if (savedCourse != null) {
			responseMap.put(DATA, TO_COURSE_DTO.apply(savedCourse));
			responseMap.put(MESSAGE, "Course Created Successfully");
			responseMap.put(SUCCESS, true);
		} else {
			responseMap.put(MESSAGE, "Course Creation Failed");
			responseMap.put(SUCCESS, false);
		}

		return new ResponseEntity<>(responseMap, HttpStatus.CREATED);
	}

	@Override
	public ResponseEntity<EnumMap<ApiResponseEnum, Object>> updateCourse(Long id, CourseDto courseDto, MultipartFile image)
			throws IOException {
		EnumMap<ApiResponseEnum, Object> responseMap = new EnumMap<>(ApiResponseEnum.class);

		Course existingCourse = courseRepo.findById(id)
				.orElseThrow(() -> new ResourceNotFound("Course", "id", id.toString()));
		
		Document document = FileUploadUtil.uploadFile(image, IMAGE, COURSE_BANNER);
		courseDto.setImage(document.getData());
		existingCourse.setName(courseDto.getName());
		existingCourse.setDescription(courseDto.getDescription());
		existingCourse.setFee(courseDto.getFee());
		existingCourse.setSyllabus(courseDto.getSyllabus());
		existingCourse.setDuration(courseDto.getDuration());
		existingCourse.setStatus(courseDto.getStatus());
		existingCourse.setLevel(courseDto.getLevel());
		existingCourse.setLanguage(courseDto.getLanguage());
		existingCourse.setStartDate(courseDto.getStartDate());
		existingCourse.setEndDate(courseDto.getEndDate());
		existingCourse.setImage(courseDto.getImage());
	//	amazonBucketService.deleteFileByUrl(existingCourse.getImageUrl());
	//  String imageUrl = amazonBucketService.uploadFile(image);
		//  existingCourse.setImageUrl(imageUrl);
		Course updatedCourse = courseRepo.save(existingCourse);
		responseMap.put(DATA, TO_COURSE_DTO.apply(updatedCourse));
		responseMap.put(MESSAGE, "Course Updated Successfully");
		responseMap.put(SUCCESS, true);

		return new ResponseEntity<>(responseMap, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<EnumMap<ApiResponseEnum, Object>> deleteCourse(Long id) {
		EnumMap<ApiResponseEnum, Object> responseMap = new EnumMap<>(ApiResponseEnum.class);
		Course course = courseRepo.findById(id).orElseThrow(() -> new ResourceNotFound("Course", "id", id.toString()));
		courseRepo.delete(course);
		responseMap.put(MESSAGE, "Course Deleted Successfully");
		responseMap.put(SUCCESS, true);
		return new ResponseEntity<>(responseMap, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<EnumMap<ApiResponseEnum, Object>> getCourse(Long id) {
		EnumMap<ApiResponseEnum, Object> responseMap = new EnumMap<>(ApiResponseEnum.class);
		Course course = courseRepo.findById(id).orElseThrow(() -> new ResourceNotFound("Course", "id", id.toString()));
		responseMap.put(DATA, TO_COURSE_DTO.apply(course));
		responseMap.put(SUCCESS, true);
		return new ResponseEntity<>(responseMap, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<EnumMap<ApiResponseEnum, Object>> getAllCourses() {
		EnumMap<ApiResponseEnum, Object> responseMap = new EnumMap<>(ApiResponseEnum.class);
		List<Course> courses = courseRepo.findAll();

		responseMap.put(DATA, TO_COURSE_DTOS.apply(courses));
		responseMap.put(SUCCESS, true);

		return new ResponseEntity<>(responseMap, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<EnumMap<ApiResponseEnum, Object>> getCoursesByCategory(Long categoryId) {
		EnumMap<ApiResponseEnum, Object> responseMap = new EnumMap<>(ApiResponseEnum.class);

		List<Course> courses = courseRepo.findByCategoryId(categoryId);

		if (courses.isEmpty()) {
			responseMap.put(MESSAGE, "No courses found for the given category");
			responseMap.put(SUCCESS, false);
		} else {
			responseMap.put(DATA, TO_COURSE_DTOS.apply(courses));
			responseMap.put(SUCCESS, true);
		}

		return new ResponseEntity<>(responseMap, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<EnumMap<ApiResponseEnum, Object>> getCoursesByStatus(String status) {
		EnumMap<ApiResponseEnum, Object> responseMap = new EnumMap<>(ApiResponseEnum.class);

		List<Course> courses = courseRepo.findByStatus(status);

		if (courses.isEmpty()) {
			responseMap.put(MESSAGE, "No courses found for the given status");
			responseMap.put(SUCCESS, false);
		} else {
			responseMap.put(DATA, TO_COURSE_DTOS.apply(courses));
			responseMap.put(SUCCESS, true);
		}

		return new ResponseEntity<>(responseMap, HttpStatus.OK);
	}
}
