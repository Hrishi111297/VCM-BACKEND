package com.bill.tech.service;

import java.io.IOException;
import java.util.EnumMap;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import com.bill.tech.enums.ApiResponse;
import com.bill.tech.payload.request.AddressDto;
import com.bill.tech.payload.request.EducationDto;
import com.bill.tech.payload.request.GuardianDetailDto;
import com.bill.tech.payload.request.UserMasterDataRequestDto;

public interface UserMasterService {
	public List<UserMasterDataRequestDto> getAllUsers();

	public ResponseEntity<EnumMap<ApiResponse, Object>> addUser(UserMasterDataRequestDto user);

	public ResponseEntity<EnumMap<ApiResponse, Object>> uploadProfilePicture(Long id, MultipartFile file)
			throws IOException;

	public ResponseEntity<?> retriveProfileImage(Long id);

	ResponseEntity<EnumMap<ApiResponse, Object>> updateUser(UserMasterDataRequestDto userDto);

	ResponseEntity<EnumMap<ApiResponse, Object>> updateAdress(AddressDto addressDto);

	ResponseEntity<EnumMap<ApiResponse, Object>> updateGaurdianDetails(GuardianDetailDto guardianDetailDto);

	ResponseEntity<EnumMap<ApiResponse, Object>> updateEducation(EducationDto educationDetailDto);

	ResponseEntity<EnumMap<ApiResponse, Object>> uploadDocument(Long userId, MultipartFile file, String documentType)
			throws IOException;



	ResponseEntity<?> retriveProfileOtherDocs(Long id, String docType);
}
