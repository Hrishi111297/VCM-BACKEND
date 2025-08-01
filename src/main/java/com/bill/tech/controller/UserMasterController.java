package com.bill.tech.controller;

import static com.bill.tech.constants.ApiConstants.*;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bill.tech.payload.request.UserMasterDataRequestDto;
import com.bill.tech.service.UserMasterService;
import com.bill.tech.util.AuditAwareUtil;

import lombok.RequiredArgsConstructor;
/**
 * @author Hrishikesh Mahadik
 * @since 11/01/2025
 * @version 1.0
 *
 */
@RestController
@RequestMapping(CATEGORY)
@RequiredArgsConstructor
public class UserMasterController {
	
	private final UserMasterService userMasterService;

	@GetMapping("/getAll")
	public ResponseEntity<List<UserMasterDataRequestDto>> getAllUsers() {
			return new ResponseEntity<List<UserMasterDataRequestDto>>(this.userMasterService.getAllUsers(), HttpStatus.OK);
	}
}
