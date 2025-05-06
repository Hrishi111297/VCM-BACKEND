package com.bill.tech.service.impl;

import static com.bill.tech.constants.ApiMessages.ADDED;
import static com.bill.tech.constants.ApiMessages.BATCH;
import static com.bill.tech.constants.ApiMessages.COURSE;
import static com.bill.tech.constants.ApiMessages.DELETED;
import static com.bill.tech.constants.ApiMessages.FETCHED;
import static com.bill.tech.constants.ApiMessages.FOR;
import static com.bill.tech.constants.ApiMessages.NOT_FOUND;
import static com.bill.tech.constants.ApiMessages.STATUS;
import static com.bill.tech.constants.ApiMessages.UPDATED;
import static com.bill.tech.dto_mapper.BatchMapper.TO_BATCH;
import static com.bill.tech.dto_mapper.BatchMapper.TO_BATCH_DTO;
import static com.bill.tech.dto_mapper.BatchMapper.TO_BATCH_DTOS;

import java.util.EnumMap;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.bill.tech.entity.Batch;
import com.bill.tech.entity.Course;
import com.bill.tech.enums.ApiResponseEnum;
import com.bill.tech.exception.ResourceNotFound;
import com.bill.tech.payload.request.BatchDto;
import com.bill.tech.payload.response.ApiResponse;
import com.bill.tech.repository.BatchRepo;
import com.bill.tech.repository.CourseRepo;
import com.bill.tech.service.BatchService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class BatchSeviceImpl implements BatchService {

	private final BatchRepo batchRepo;
	private final CourseRepo courseRepo;

	@Override
	public ResponseEntity<EnumMap<ApiResponseEnum, Object>> createbatch(BatchDto batchDto)
			throws java.io.IOException {
		
		Batch batch= TO_BATCH.apply(batchDto).orElseThrow(() -> new ResourceNotFound(BATCH));
		Course course=getCourseById(batchDto.getCourseId());
		batch.setCourse(course);
		Batch savedBatch=batchRepo.save(batch);
		return ApiResponse.buildSuccesResponse(TO_BATCH_DTO.apply(savedBatch), BATCH + ADDED, HttpStatus.CREATED);
		
	}

	private Batch getBatchById(Long id) {
		Batch batch=batchRepo.findById(id).orElseThrow(() -> new ResourceNotFound("Batch", "Id", String.valueOf(id)));
				
		return batch;
	}

	@Override
	public ResponseEntity<EnumMap<ApiResponseEnum, Object>> updateBatch(Long id, BatchDto batchDto
			)  {

		Batch existingBatch = getBatchById(id);


	    existingBatch.setName(batchDto.getName());
	    existingBatch.setStartDate(batchDto.getStartDate());
	    existingBatch.setEndDate(batchDto.getEndDate());
	    existingBatch.setDuration(batchDto.getDuration());
	    existingBatch.setOpen(batchDto.isOpen());
	    Batch savedBatch = batchRepo.save(existingBatch);
		return ApiResponse.buildSuccesResponse(TO_BATCH_DTO.apply(savedBatch), BATCH + UPDATED, HttpStatus.OK);
	}

	private Course getCourseById(Long id) {
		return courseRepo.findById(id).orElseThrow(() -> new ResourceNotFound("Course", "id", id.toString()));
	}

	@Override
	public ResponseEntity<EnumMap<ApiResponseEnum, Object>> deleteBatch(Long id) {
		Batch batch = getBatchById(id);
		batchRepo.delete(batch);
		return ApiResponse.buildSuccesResponse(null, BATCH + DELETED, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<EnumMap<ApiResponseEnum, Object>> getBatch(Long id) {
		Batch batch = getBatchById(id);
		return ApiResponse.buildSuccesResponse(TO_BATCH_DTO.apply(batch), BATCH + FETCHED, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<EnumMap<ApiResponseEnum, Object>> getAllBatches() {
		List<Batch> batches = batchRepo.findAll();

		return ApiResponse.buildSuccesResponse(TO_BATCH_DTOS.apply(batches), BATCH + FETCHED, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<EnumMap<ApiResponseEnum, Object>> getBatchByCourse(Long courseId) {
		List<Batch> batches = batchRepo.findByCourseId(courseId);
		if (batches.isEmpty()) {
			return ApiResponse.buildfailureApiResponse1(null, BATCH + NOT_FOUND + FOR + COURSE,
					HttpStatus.NOT_FOUND);
		} else {
			return ApiResponse.buildSuccesResponse(TO_BATCH_DTOS.apply(batches), BATCH + FETCHED, HttpStatus.OK);
		}
	}

	@Override
	public ResponseEntity<EnumMap<ApiResponseEnum, Object>> getBatchesByStatus(boolean status) {

		List<Batch> batches = batchRepo.findByIsOpen(status);
		if (batches.isEmpty()) {
			return ApiResponse.buildfailureApiResponse1(null, BATCH + NOT_FOUND + FOR + STATUS, HttpStatus.NOT_FOUND);
		} else {
			return ApiResponse.buildSuccesResponse(TO_BATCH_DTOS.apply(batches), BATCH + FETCHED, HttpStatus.OK);
		}

	}
}
