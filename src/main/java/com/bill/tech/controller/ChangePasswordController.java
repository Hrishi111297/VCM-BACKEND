package com.bill.tech.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.tags.Tag;
/**
 * @author Hrishikesh Mahadik
 * @since 11/01/2025
 * @version 1.0
 *
 */
@RestController
@RequestMapping("/auth")
@Tag(name = "ChangePasswordController", description = "This Section Gives Us The API Endpoint Related To The ChangePasswordController")
public class ChangePasswordController {
	@Autowired
	

	@GetMapping("/stay")
	String stay() {
		return "Hello World";
	}


}
