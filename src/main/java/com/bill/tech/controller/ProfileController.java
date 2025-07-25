package com.bill.tech.controller;

import static com.bill.tech.constants.ApiConstants.UPDATE_ADDRESS;



import static com.bill.tech.constants.ApiConstants.UPDATE_GAURDIAN_DETAILS;
import static com.bill.tech.constants.ApiConstants.PROFILE;
import static com.bill.tech.constants.ApiConstants.*;
import static com.bill.tech.constants.ApiConstants.UPDATE_PERSONAL_DETAILS;
import static com.bill.tech.constants.ApiConstants.UPLOAD_PROFILE;
import static org.springframework.security.core.context.SecurityContextHolder.getContext;
import static com.bill.tech.constants.ApiConstants.UPDATE_EDUCATION_DETAILS;
import static com.bill.tech.constants.ApiConstants.RETRIVE_DOCS;
import static com.bill.tech.constants.ApiConstants.UPLOAD_OTHER_DOCUMENT;
import static java.util.Objects.nonNull;

import java.io.IOException;
import java.util.EnumMap;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.bill.tech.entity.UserMaster;
import com.bill.tech.marker.UpdateValidation;
import com.bill.tech.payload.request.AddressDto;
import com.bill.tech.payload.request.EducationDto;
import com.bill.tech.payload.request.GuardianDetailDto;
import com.bill.tech.payload.request.UserMasterDataRequestDto;
import com.bill.tech.service.UserMasterService;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

/**
 * @author Hrishikesh Mahadik
 * @since 11/01/2025
 * @version 1.0
 *
 */
@RequestMapping(PROFILE)
@Tag(name = "PROFILE", description = "This Section Gives Us The API Endpoint Related To The PROFILES")

@RequiredArgsConstructor
@RestController
public class ProfileController {
	private final UserMasterService userMasterService;

	@PostMapping(UPLOAD_PROFILE)
	public ResponseEntity<EnumMap<com.bill.tech.enums.ApiResponseEnum, Object>> ProfilePicture(@PathVariable Long userId,
			@RequestParam("profile_pic") MultipartFile file) throws IOException {
		return userMasterService.uploadProfilePicture(userId, file);
	}

	@GetMapping(RETRIVE_PROFILE_PHOTO)
	public ResponseEntity<?> getProfilePicture(@PathVariable Long id) {

		return userMasterService.retriveProfileImage(id);

	}

	@PutMapping(UPDATE_PERSONAL_DETAILS)
	public ResponseEntity<EnumMap<com.bill.tech.enums.ApiResponseEnum, Object>> updateProfile(
			@Validated(UpdateValidation.class) @org.springframework.web.bind.annotation.RequestBody UserMasterDataRequestDto user)
			throws IOException {
		return userMasterService.updateUser(user);
	}

	@PutMapping(UPDATE_ADDRESS)
	public ResponseEntity<EnumMap<com.bill.tech.enums.ApiResponseEnum, Object>> updateAdress(
			@org.springframework.web.bind.annotation.RequestBody AddressDto addressDto) {
		return userMasterService.updateAdress(addressDto);
	}

	@PutMapping(UPDATE_GAURDIAN_DETAILS)
	public ResponseEntity<EnumMap<com.bill.tech.enums.ApiResponseEnum, Object>> updateGaurdian(
			@org.springframework.web.bind.annotation.RequestBody GuardianDetailDto guardianDetailDto)
			throws IOException {
		return userMasterService.updateGaurdianDetails(guardianDetailDto);
	}

	@PutMapping(UPDATE_EDUCATION_DETAILS)
	public ResponseEntity<EnumMap<com.bill.tech.enums.ApiResponseEnum, Object>> updateEducationDetails(
			@org.springframework.web.bind.annotation.RequestBody EducationDto educationDto) throws IOException {
		return userMasterService.updateEducation(educationDto);
	}

	@PostMapping(UPLOAD_OTHER_DOCUMENT)
	public ResponseEntity<EnumMap<com.bill.tech.enums.ApiResponseEnum, Object>> uploadOtherDocument(
			@RequestParam("userID") Long userId, @RequestParam("doc") MultipartFile file,
			@RequestParam("docType") String documentType) throws IOException {
		return userMasterService.uploadDocument(userId, file, documentType);
	}

	@GetMapping(RETRIVE_DOCS)
	public ResponseEntity<?> retriveDocument(@RequestParam("userID") Long userId,
			@RequestParam("docType") String documentType) throws IOException {
		return userMasterService.retriveProfileOtherDocs(userId, documentType);
	}
	
	@GetMapping(RETRIVE_PROFILE_DETAILS)
	public ResponseEntity<EnumMap<com.bill.tech.enums.ApiResponseEnum, Object>>getProfileData(){
		  UserMaster details =null;
	
		 if (nonNull(getContext()) && nonNull(getContext().getAuthentication())
		            && getContext().getAuthentication().getPrincipal() instanceof UserMaster) {
		        details = (UserMaster) getContext().getAuthentication().getPrincipal();
		
	}
		 return userMasterService.getUserProfile( details.getId());
}}
	