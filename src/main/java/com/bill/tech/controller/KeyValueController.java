package com.bill.tech.controller;
import static com.bill.tech.constants.ApiConstants.*;

import java.util.EnumMap;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bill.tech.enums.ApiResponse;
import com.bill.tech.payload.request.KeyValueDto;
import com.bill.tech.service.KeyValueService;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
/**
 * @author Hrishikesh Mahadik
 * @since 11/01/2025
 * @version 1.0
 *
 */
@RequestMapping(KEY_VALUE)
@Tag(name = "KEY_VALUE", description = "This Section Gives Us The API Endpoint Related To The KEY_VALUE")

@RequiredArgsConstructor
@RestController
public class KeyValueController {
    private final KeyValueService keyValueService;
	
    @PostMapping(CREATE_KEY_VALUE)
    public ResponseEntity<EnumMap<ApiResponse, Object>> saveOrUpdate(@RequestBody KeyValueDto keyValue) {
    return  keyValueService.saveOrUpdate(keyValue);
        
    }
    

    @GetMapping(GET_KEY_VALUE_ALL)
    public 	ResponseEntity<EnumMap<ApiResponse, Object>> getAll() {
    	return keyValueService.getAll();
    }

     @GetMapping(GET__KEY_VALUE)
    public ResponseEntity<EnumMap<ApiResponse, Object>> getByKey(@PathVariable String key) {
        return keyValueService.getByKey(key);
    }

     @DeleteMapping(DELETE_KEY_VALUE)
    public ResponseEntity<EnumMap<ApiResponse, Object>> deleteById(@PathVariable String key) {
      return  keyValueService.deleteByKey(key);
        
    }
}






