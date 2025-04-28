package com.bill.tech.service.impl;

import static com.bill.tech.constants.ApiMessages.ADDED;
import static com.bill.tech.constants.ApiMessages.DELETED;
import static com.bill.tech.constants.ApiMessages.FETCHED;
import static com.bill.tech.constants.ApiMessages.KEY_VALUE;
import static com.bill.tech.constants.ApiMessages.UPDATED;
import static com.bill.tech.dto_mapper.KeyValueMapper.TO_KEYVALUE;
import static com.bill.tech.dto_mapper.KeyValueMapper.TO_KEYVALUE_DTO;
import static com.bill.tech.dto_mapper.KeyValueMapper.TO_KEYVALUE_DTOS;

import java.util.EnumMap;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.bill.tech.entity.KeyValue;
import com.bill.tech.enums.ApiResponseEnum;
import com.bill.tech.exception.ResourceNotFound;
import com.bill.tech.payload.request.KeyValueDto;
import com.bill.tech.payload.response.ApiResponse;
import com.bill.tech.repository.KeyValueRepo;
import com.bill.tech.service.KeyValueService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Service
@Slf4j
public class KeyValueServiceImpl implements KeyValueService {

	private final KeyValueRepo keyValueRepo;

	@Override
	public ResponseEntity<EnumMap<ApiResponseEnum, Object>> saveOrUpdate(KeyValueDto keyValueDto) {
		log.info("Inside saveOrUpdate method...{}", keyValueDto);
		KeyValue existingKeyValue = getKeyValueByKey(keyValueDto.getKey());
		KeyValue save = null;

		if (existingKeyValue == null) {
			save = keyValueRepo.save(TO_KEYVALUE.apply(keyValueDto).orElseThrow(() -> new ResourceNotFound(KEY_VALUE)));
			return ApiResponse.buildSuccesResponse(TO_KEYVALUE_DTO.apply(save), KEY_VALUE + ADDED, HttpStatus.CREATED);
		} else {
			existingKeyValue.setValue(keyValueDto.getValue());
			save = keyValueRepo.save(existingKeyValue);
			return ApiResponse.buildSuccesResponse(TO_KEYVALUE_DTO.apply(save), KEY_VALUE + UPDATED, HttpStatus.OK);
		}
	}

	private KeyValue getKeyValueByKey(String key) {
		return keyValueRepo.findByKey(key).orElse(null);
	}

	@Override
	public ResponseEntity<EnumMap<ApiResponseEnum, Object>> getByKey(String key) {
		log.info("Fetching key-value by key...{}", key);
		KeyValue keyValue = getKeyValueByKey(key);

		return ApiResponse.buildSuccesResponse(TO_KEYVALUE_DTO.apply(keyValue), KEY_VALUE + FETCHED, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<EnumMap<ApiResponseEnum, Object>> deleteByKey(String key) {
		log.info("Deleting key-value by key...{}", key);
		KeyValue keyValue = getKeyValueByKey(key);
		keyValueRepo.deleteById(keyValue.getId());
		return ApiResponse.buildSuccesResponse(null, KEY_VALUE + DELETED, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<EnumMap<ApiResponseEnum, Object>> getAll() {
		log.info("Fetching all key-value pairs...");
		return ApiResponse.buildSuccesResponse(TO_KEYVALUE_DTOS.apply(keyValueRepo.findAll()), KEY_VALUE + FETCHED,
				HttpStatus.OK);
	}
}
