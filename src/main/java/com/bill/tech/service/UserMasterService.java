package com.bill.tech.service;

import java.io.IOException;
import java.util.EnumMap;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import com.bill.tech.enums.ApiResponseEnum;
import com.bill.tech.payload.request.AddressDto;
import com.bill.tech.payload.request.EducationDto;
import com.bill.tech.payload.request.GuardianDetailDto;
import com.bill.tech.payload.request.UserMasterDataRequestDto;

public interface UserMasterService {
	public List<UserMasterDataRequestDto> getAllUsers();

	public ResponseEntity<EnumMap<ApiResponseEnum, Object>> addUser(UserMasterDataRequestDto user);

	public ResponseEntity<EnumMap<ApiResponseEnum, Object>> uploadProfilePicture(Long id, MultipartFile file)
			throws IOException;

	public ResponseEntity<?> retriveProfileImage(Long id);

	ResponseEntity<EnumMap<ApiResponseEnum, Object>> updateUser(UserMasterDataRequestDto userDto);

	ResponseEntity<EnumMap<ApiResponseEnum, Object>> updateAdress(AddressDto addressDto);

	ResponseEntity<EnumMap<ApiResponseEnum, Object>> updateGaurdianDetails(GuardianDetailDto guardianDetailDto);

	ResponseEntity<EnumMap<ApiResponseEnum, Object>> updateEducation(EducationDto educationDetailDto);

	ResponseEntity<EnumMap<ApiResponseEnum, Object>> uploadDocument(Long userId, MultipartFile file, String documentType)
			throws IOException;



	ResponseEntity<?> retriveProfileOtherDocs(Long id, String docType);

	ResponseEntity<EnumMap<ApiResponseEnum, Object>> getUserProfile(Long userId);
}
