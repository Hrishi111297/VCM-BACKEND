package com.bill.tech.service.impl;

import static com.bill.tech.dto_mapper.KeyValueMapper.*;

import static com.bill.tech.enums.ApiResponse. *;
import java.util.EnumMap;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.bill.tech.entity.KeyValue;
import com.bill.tech.enums.ApiResponse;
import com.bill.tech.exception.ResourceNotFound;
import com.bill.tech.payload.request.KeyValueDto;
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
    public ResponseEntity<EnumMap<ApiResponse, Object>> saveOrUpdate(KeyValueDto keyValueDto) {
        log.info("Inside saveOrUpdate method...{}", keyValueDto);
        EnumMap<ApiResponse, Object> response = new EnumMap<>( ApiResponse.class);

        KeyValue existingKeyValue = keyValueRepo.findByKey(keyValueDto.getKey());
        KeyValue save = null;

        if (existingKeyValue == null) {
            save = keyValueRepo.save(TO_KEYVALUE.apply(keyValueDto)
                    .orElseThrow(() -> new ResourceNotFound("KeyValue")));
        } else {
            existingKeyValue.setValue(keyValueDto.getValue());
            save = keyValueRepo.save(existingKeyValue);
        }

        if (save != null) {
            response.put( SUCCESS, true);
            response.put( DATA, TO_KEYVALUE_DTO.apply(save));
            response.put( MESSAGE, "Key-Value Saved/Updated Successfully");
        } else {
            response.put( SUCCESS, false);
            response.put( MESSAGE, "Key-Value Not Saved");
        }

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<EnumMap<ApiResponse, Object>> getByKey(String key) {
        log.info("Fetching key-value by key...{}", key);
        EnumMap<ApiResponse, Object> response = new EnumMap<>( ApiResponse.class);

        KeyValue keyValue = keyValueRepo.findByKey(key);
        if (keyValue == null) {
            response.put( SUCCESS, false);
            response.put( MESSAGE, "Key-Value Not Found");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

        response.put( SUCCESS, true);
        response.put( DATA, TO_KEYVALUE_DTO.apply(keyValue));
        response.put( MESSAGE, "Key-Value Fetched Successfully");

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<EnumMap<ApiResponse, Object>> deleteByKey(String key) {
        log.info("Deleting key-value by key...{}", key);
        EnumMap<ApiResponse, Object> response = new EnumMap<>( ApiResponse.class);

        KeyValue keyValue = keyValueRepo.findByKey(key);
        if (keyValue == null) {
            response.put( SUCCESS, false);
            response.put( MESSAGE, "Key-Value Not Found");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

        keyValueRepo.deleteById(keyValue.getId());

        response.put( SUCCESS, true);
        response.put( MESSAGE, "Key-Value Deleted Successfully");

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<EnumMap<ApiResponse, Object>> getAll() {
        log.info("Fetching all key-value pairs...");
        EnumMap<ApiResponse, Object> response = new EnumMap<>( ApiResponse.class);

        response.put( SUCCESS, true);
        response.put( DATA, TO_KEYVALUE_DTOS.apply(keyValueRepo.findAll()));
        response.put( MESSAGE, "Key-Values Fetched Successfully");

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
