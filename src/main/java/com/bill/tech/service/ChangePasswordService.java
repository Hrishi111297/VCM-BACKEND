/**
 * 
 */
package com.bill.tech.service;

import java.util.EnumMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;

import com.bill.tech.enums.ApiResponse;
import com.bill.tech.payload.request.PasswordHistoryDTO;

public interface ChangePasswordService {

	ResponseEntity<EnumMap<ApiResponse, Object>> verifyOtp(String userId);

	ResponseEntity<EnumMap<ApiResponse, Object>> changePassword(String email, Long otp, PasswordHistoryDTO optDto);

	
}
