package com.bill.tech.service;

import java.io.IOException;
import java.util.EnumMap;

import org.springframework.http.ResponseEntity;

import com.bill.tech.enums.ApiResponseEnum;
import com.bill.tech.payload.request.BatchDto;

public interface BatchService {

	ResponseEntity<EnumMap<ApiResponseEnum, Object>> createbatch(BatchDto batchDto) throws IOException;

}
