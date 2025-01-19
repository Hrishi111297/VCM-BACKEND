package com.bill.tech.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bill.tech.payload.request.UserMasterDataRequestDto;
import com.bill.tech.service.UserMasterService;

@RestController
@RequestMapping("api")
public class UserMasterController {
	@Autowired
	UserMasterService userMasterService;

	@GetMapping("/getAll")
	public ResponseEntity<List<UserMasterDataRequestDto>> getAllUsers() {
		return new ResponseEntity<List<UserMasterDataRequestDto>>(this.userMasterService.getAllUsers(), HttpStatus.OK);
	}
}
