package com.bill.tech.service;

import java.io.IOException;
import java.util.EnumMap;

import org.springframework.http.ResponseEntity;

import com.bill.tech.enums.ApiResponseEnum;
import com.bill.tech.payload.request.BatchDto;

public interface BatchService {

	ResponseEntity<EnumMap<ApiResponseEnum, Object>> createbatch(BatchDto batchDto) throws IOException;

	ResponseEntity<EnumMap<ApiResponseEnum, Object>> updateBatch(Long id, BatchDto batchDto);

	ResponseEntity<EnumMap<ApiResponseEnum, Object>> deleteBatch(Long id);

	ResponseEntity<EnumMap<ApiResponseEnum, Object>> getBatchesByStatus(boolean status);

	ResponseEntity<EnumMap<ApiResponseEnum, Object>> getBatchByCourse(Long courseId);

	ResponseEntity<EnumMap<ApiResponseEnum, Object>> getAllBatches();

	ResponseEntity<EnumMap<ApiResponseEnum, Object>> getBatch(Long id);

}
