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
import org.springframework.web.bind.annotation.RestController;

import com.bill.tech.enums.ApiResponseEnum;
import com.bill.tech.payload.request.BatchDto;
import com.bill.tech.service.BatchService;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
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
	
	private final BatchService batchService;

	@PostMapping(CREATE_BATCH)
	public ResponseEntity<EnumMap<ApiResponseEnum, Object>> createbatch(@RequestBody BatchDto batchDto) throws IOException {
		return batchService.createbatch(batchDto);
	}

	@PutMapping(UPDATE_BATCH)
	public ResponseEntity<EnumMap<ApiResponseEnum, Object>> updateBatch(@PathVariable Long id,
			@RequestBody BatchDto batchDto)  {
		return batchService.updateBatch(id,batchDto);
	}

	@DeleteMapping(DELETE_BATCH)
	public ResponseEntity<EnumMap<ApiResponseEnum, Object>> deleteBatch(@PathVariable Long id) {
		return batchService.deleteBatch(id);
	}

	@GetMapping(GET_BATCH)
	public ResponseEntity<EnumMap<ApiResponseEnum, Object>> getBatch(@PathVariable Long id) {
		return batchService.getBatch(id);
	}

	@GetMapping(GET_ALL_BATCHES)
	public ResponseEntity<EnumMap<ApiResponseEnum, Object>> getAllBatches() {
		return batchService.getAllBatches();
	}

	@GetMapping(GET_BATCH_BY_COURSE)
	public ResponseEntity<EnumMap<ApiResponseEnum, Object>> getBatchByCourse(@PathVariable Long courseId) {
		return batchService.getBatchByCourse(courseId);
	}

	@GetMapping(GET_COURSES_BY_STATUS)
	public ResponseEntity<EnumMap<ApiResponseEnum, Object>> getCoursesByStatus(@PathVariable boolean status) {
		return batchService.getBatchesByStatus(status);
	}
}
