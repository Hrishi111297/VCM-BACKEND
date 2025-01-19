package com.bill.tech.service;

import java.util.EnumMap;

import org.springframework.http.ResponseEntity;

import com.bill.tech.enums.ApiResponse;
import com.bill.tech.payload.request.KeyValueDto;

public interface KeyValueService {

	ResponseEntity<EnumMap<ApiResponse, Object>> getAll();

	ResponseEntity<EnumMap<ApiResponse, Object>> saveOrUpdate(KeyValueDto keyValueDto);

	ResponseEntity<EnumMap<ApiResponse, Object>> deleteByKey(String key);

	ResponseEntity<EnumMap<ApiResponse, Object>> getByKey(String key);



}
