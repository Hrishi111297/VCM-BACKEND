package com.bill.tech.payload.response;

import static lombok.AccessLevel.PRIVATE;

import java.util.EnumMap;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.bill.tech.enums.ApiResponseEnum;

import lombok.NoArgsConstructor;
@NoArgsConstructor(access = PRIVATE)
public class ApiResponse {

	public static ResponseEntity<EnumMap<ApiResponseEnum, Object>>buildSuccesResponse (Object data, String message, HttpStatus status) {
		 EnumMap<ApiResponseEnum, Object> response = new EnumMap<>(ApiResponseEnum.class);
		  response.put(ApiResponseEnum.SUCCESS, true);
	        response.put(ApiResponseEnum.DATA, data);
	        response.put(ApiResponseEnum.MESSAGE, message);
		 
	        return new ResponseEntity<>(response, status);
	}
	public static ResponseEntity<EnumMap<ApiResponseEnum, Object>> buildfailureApiResponse1(Object data, String message, HttpStatus status) {
		 EnumMap<ApiResponseEnum, Object> response = new EnumMap<>(ApiResponseEnum.class);
		  response.put(ApiResponseEnum.SUCCESS, false);
	        response.put(ApiResponseEnum.DATA, data);
	        response.put(ApiResponseEnum.MESSAGE, message);
	        return new ResponseEntity<>(response, status);
	}
}
