package com.bill.tech.service;

import java.util.EnumMap;

import org.springframework.http.ResponseEntity;

import com.bill.tech.enums.ApiResponseEnum;
import com.bill.tech.payload.request.KeyValueDto;

public interface KeyValueService {

	ResponseEntity<EnumMap<ApiResponseEnum, Object>> getAll();

	ResponseEntity<EnumMap<ApiResponseEnum, Object>> saveOrUpdate(KeyValueDto keyValueDto);

	ResponseEntity<EnumMap<ApiResponseEnum, Object>> deleteByKey(String key);

	ResponseEntity<EnumMap<ApiResponseEnum, Object>> getByKey(String key);



}
