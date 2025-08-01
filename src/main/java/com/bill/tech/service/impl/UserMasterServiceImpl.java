package com.bill.tech.service.impl;

import static com.bill.tech.constants.FileTypes.IMAGE;

import static com.bill.tech.constants.FileTypes.PROFILE_PICTURE;
import static com.bill.tech.constants.RoleConstants.VCM_STUDENT;
import static com.bill.tech.dto_mapper.AdressMapper.TO_ADDRESS;
import static com.bill.tech.dto_mapper.AdressMapper.TO_ADDRESS_DTO;
import static com.bill.tech.dto_mapper.EducationMapper.TO_EDUCATION;
import static com.bill.tech.dto_mapper.EducationMapper.TO_EDUCATION_DTO_COLLECTION;
import static com.bill.tech.dto_mapper.GaurdianMapper.TO_GUARDIAN;
import static com.bill.tech.dto_mapper.GaurdianMapper.TO_GUARDIAN_DTO;
import static com.bill.tech.dto_mapper.UserMasterMapper.TO_USERMASTER;
import static com.bill.tech.dto_mapper.UserMasterMapper.*;
import static java.util.Objects.nonNull;

import java.io.IOException;
import java.util.EnumMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.bill.tech.entity.Address;
import com.bill.tech.entity.Document;
import com.bill.tech.entity.Education;
import com.bill.tech.entity.GuardianDetail;
import com.bill.tech.entity.Role;
import com.bill.tech.entity.UserMaster;
import com.bill.tech.enums.ApiResponseEnum;
import com.bill.tech.exception.ResourceNotFound;
import com.bill.tech.payload.request.AddressDto;
import com.bill.tech.payload.request.DocumentDto;
import com.bill.tech.payload.request.EducationDto;
import com.bill.tech.payload.request.GuardianDetailDto;
import com.bill.tech.payload.request.UserMasterDataRequestDto;
import com.bill.tech.payload.response.UserProfileDto;
import com.bill.tech.repository.AddressRepo;
import com.bill.tech.repository.DocumentRepo;
import com.bill.tech.repository.EducationRepo;
import com.bill.tech.repository.GuardianRepo;
import com.bill.tech.repository.RoleRepo;
import com.bill.tech.repository.UserMasterRepo;
import com.bill.tech.service.UserMasterService;
import com.bill.tech.util.FileUploadUtil;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Service
@Slf4j
public class UserMasterServiceImpl implements UserMasterService {

	private final UserMasterRepo userMasterRepo;
	private final DocumentRepo documentRepo;

	private final ModelMapper modelMapper;
	private final RoleRepo roleRepo;
	private final AddressRepo addressRepo;
	private final GuardianRepo guardianRepo;
	private final EducationRepo educationRepo;

	@Override
	public ResponseEntity<EnumMap<ApiResponseEnum, Object>> addUser(UserMasterDataRequestDto userDTO) {
		EnumMap<ApiResponseEnum, Object> userMap = new EnumMap<>(ApiResponseEnum.class);
		log.info("Inside the addUser1 method...{}", userDTO);

		UserMaster user = TO_USERMASTER.apply(userDTO).orElseThrow(() -> new ResourceNotFound("User"));
		Role student = roleRepo.findByName(VCM_STUDENT).orElseThrow(() -> new ResourceNotFound("Role"));

		user.setRoles(Set.of(student));
		UserMaster savedUser = userMasterRepo.save(user);

		if (nonNull(savedUser)) {
			UserMasterDataRequestDto savedUserDTO = TO_USERMASTER_DTO.apply(savedUser)
					.orElseThrow(() -> new ResourceNotFound("User"));
			savedUserDTO.setRoleIds(savedUser.getRoles().stream().map(Role::getId) // Extract role IDs
					.collect(Collectors.toSet()));
			userMap.put(ApiResponseEnum.DATA, savedUserDTO);
			userMap.put(ApiResponseEnum.MESSAGE, "User Added Successfully");
		} else {
			userMap.put(ApiResponseEnum.MESSAGE, "User Not Added");
		}
		userMap.put(ApiResponseEnum.SUCCESS, true);
		return new ResponseEntity<>(userMap, HttpStatus.CREATED);
	}

	@Override
	public List<UserMasterDataRequestDto> getAllUsers() {
		return this.userMasterRepo.findAll().stream()
				.map(emp -> this.modelMapper.map(emp, UserMasterDataRequestDto.class)).collect(Collectors.toList());
	}

	@Override
	@Transactional
	public ResponseEntity<EnumMap<ApiResponseEnum, Object>> uploadProfilePicture(Long id, MultipartFile file)
			throws IOException {
		UserMaster user = userMasterRepo.findById(id)
				.orElseThrow(() -> new ResourceNotFound("User", "id", id.toString()));
		EnumMap<ApiResponseEnum, Object> fileMap = new EnumMap<>(ApiResponseEnum.class);
		Document savedDocument = null;
		Document document = FileUploadUtil.uploadFile(file, IMAGE, PROFILE_PICTURE);
		document.setUser(user);
		Document existingDocument = documentRepo.findByUserAndDocumentType(user, PROFILE_PICTURE);
		if (nonNull(existingDocument)) {
			existingDocument.setData(document.getData());
			existingDocument.setFileName(document.getFileName());
			existingDocument.setFileType(document.getFileType());
			savedDocument = documentRepo.save(existingDocument);
		} else {
			savedDocument = documentRepo.save(document);
		}
		if (nonNull(savedDocument)) {
			fileMap.put(ApiResponseEnum.SUCCESS, true);
			fileMap.put(ApiResponseEnum.MESSAGE, IMAGE + " uploaded successfully.");
		} else {
			fileMap.put(ApiResponseEnum.SUCCESS, false);
			fileMap.put(ApiResponseEnum.MESSAGE, IMAGE + " upload failed.");
		}

		return new ResponseEntity<>(fileMap, HttpStatus.CREATED);
	}

	@Override
	public ResponseEntity<?> retriveProfileImage(Long id) {

		UserMaster user = userMasterRepo.findById(id)
				.orElseThrow(() -> new ResourceNotFound("User", "id", id.toString()));
		Document document = documentRepo.findByUserAndDocumentType(user, PROFILE_PICTURE);
		 return FileUploadUtil.buildDocumentResponse(document);	
	}

	@Override
	public ResponseEntity<EnumMap<ApiResponseEnum, Object>> updateUser(UserMasterDataRequestDto userDto) {
		EnumMap<ApiResponseEnum, Object> userMap = new EnumMap<>(ApiResponseEnum.class);
		log.info("Inside the updateUser method...{}", userDto);

		UserMaster existingUser = userMasterRepo.findById(userDto.getId())
				.orElseThrow(() -> new ResourceNotFound("User", "id", String.valueOf(userDto.getId())));

		existingUser.setFirstName(userDto.getFirstName());
		existingUser.setMiddleName(userDto.getMiddleName());
		existingUser.setLastName(userDto.getLastName());
		existingUser.setEmailId(userDto.getEmailId());
		existingUser.setContactNumber(userDto.getContactNumber());
		existingUser.setGender(userDto.getGender());
		existingUser.setAdharNumber(userDto.getAdharNumber());
		existingUser.setBloodGroup(userDto.getBloodGroup());
		existingUser.setBirthDate(userDto.getBirthDate());
		existingUser.setBloodGroup(userDto.getBloodGroup());
//
//		Set<Role> updatedRoles = new HashSet<>(roleRepo.findAllById(userDto.getRoleIds()));
//		if (updatedRoles.isEmpty()) {
//			throw new ResourceNotFound("Roles");
//		}
//		existingUser.setRoles(updatedRoles);

		UserMaster savedUser = userMasterRepo.save(existingUser);

		if (savedUser != null) {
			UserMasterDataRequestDto savedUserDTO = TO_USERMASTER_DTO.apply(savedUser)
					.orElseThrow(() -> new ResourceNotFound("User"));
			savedUserDTO.setRoleIds(savedUser.getRoles().stream().map(Role::getId) // Extract role IDs
					.collect(Collectors.toSet()));
			userMap.put(ApiResponseEnum.DATA, savedUserDTO);
			userMap.put(ApiResponseEnum.DATA, savedUserDTO);
			userMap.put(ApiResponseEnum.MESSAGE, "User Updated Successfully");
		} else {
			userMap.put(ApiResponseEnum.MESSAGE, "User Not Updated!!");
		}
		userMap.put(ApiResponseEnum.SUCCESS, true);

		return new ResponseEntity<>(userMap, HttpStatus.OK);
	}

	@Override
	@Transactional
	public ResponseEntity<EnumMap<ApiResponseEnum, Object>> updateAdress(AddressDto addressDto) {
		EnumMap<ApiResponseEnum, Object> addressMap = new EnumMap<>(ApiResponseEnum.class);
		log.info("Inside the updateAddress method...{}", addressDto);

		UserMaster existingUser = userMasterRepo.findById(addressDto.getUserId())
				.orElseThrow(() -> new ResourceNotFound("User", "id", String.valueOf(addressDto.getUserId())));

		Address existingAddress = addressRepo.findByUserId(addressDto.getUserId());
		Address savedAddress = null;

		if (nonNull(existingAddress)) {
			existingAddress.setHouseNumber(addressDto.getHouseNumber());
			existingAddress.setStreet(addressDto.getStreet());
			existingAddress.setCity(addressDto.getCity());
			existingAddress.setState(addressDto.getState());
			existingAddress.setPincode(addressDto.getPincode());
			existingAddress.setCountry(addressDto.getCountry());
			savedAddress = addressRepo.save(existingAddress);
		} else {
			Address newAddress = TO_ADDRESS.apply(addressDto).orElseThrow(() -> new ResourceNotFound("Address"));

			newAddress.setUser(existingUser);
			savedAddress = addressRepo.save(newAddress);
		}

		if (savedAddress != null) {

			addressMap.put(ApiResponseEnum.DATA, TO_ADDRESS_DTO.apply(savedAddress));
			addressMap.put(ApiResponseEnum.MESSAGE, "Address Updated Successfully");
			addressMap.put(ApiResponseEnum.SUCCESS, true);
		} else {
			addressMap.put(ApiResponseEnum.MESSAGE, "Address Not Updated!!");
			addressMap.put(ApiResponseEnum.SUCCESS, false);
		}

		return new ResponseEntity<>(addressMap, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<EnumMap<ApiResponseEnum, Object>> updateGaurdianDetails(GuardianDetailDto guardianDetailDto) {
		EnumMap<ApiResponseEnum, Object> guardianMap = new EnumMap<>(ApiResponseEnum.class);
		log.info("Inside the updateGuardianDetails method...{}", guardianDetailDto);

		// Fetch the user by ID
		UserMaster existingUser = userMasterRepo.findById(guardianDetailDto.getUserId())
				.orElseThrow(() -> new ResourceNotFound("User", "id", String.valueOf(guardianDetailDto.getUserId())));

		// Fetch the existing guardian details by user ID
		GuardianDetail existingGuardian = guardianRepo.findByUserId(guardianDetailDto.getUserId());
		GuardianDetail savedGuardian = null;

		if (nonNull(existingGuardian)) {
			// Update the existing guardian details
			existingGuardian.setName(guardianDetailDto.getName());
			existingGuardian.setRelationship(guardianDetailDto.getRelationship());
			existingGuardian.setContactNumber(guardianDetailDto.getContactNumber());
			savedGuardian = guardianRepo.save(existingGuardian);
		} else {
			GuardianDetail newGuardian = TO_GUARDIAN.apply(guardianDetailDto)
					.orElseThrow(() -> new ResourceNotFound("GuardianDetail"));
			newGuardian.setUser(existingUser);
			savedGuardian = guardianRepo.save(newGuardian);
		}

		if (savedGuardian != null) {
			guardianMap.put(ApiResponseEnum.DATA, TO_GUARDIAN_DTO.apply(savedGuardian));
			guardianMap.put(ApiResponseEnum.MESSAGE, "Guardian Details Updated Successfully");
			guardianMap.put(ApiResponseEnum.SUCCESS, true);
		} else {
			guardianMap.put(ApiResponseEnum.MESSAGE, "Guardian Details Not Updated!!");
			guardianMap.put(ApiResponseEnum.SUCCESS, false);
		}

		return new ResponseEntity<>(guardianMap, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<EnumMap<ApiResponseEnum, Object>> updateEducation(EducationDto educationDetailDto) {
	    EnumMap<ApiResponseEnum, Object> educationMap = new EnumMap<>(ApiResponseEnum.class);
	    log.info("Inside the updateEducation method...{}", educationDetailDto);

	    // Fetch the user by ID
	    UserMaster existingUser = userMasterRepo.findById(educationDetailDto.getUserId())
	            .orElseThrow(() -> new ResourceNotFound("User", "id", String.valueOf(educationDetailDto.getUserId())));

	  
	   
	        Education newEducation = TO_EDUCATION.apply(educationDetailDto)
	                .orElseThrow(() -> new ResourceNotFound("EducationDetail"));
	        boolean exists=educationRepo.existsByUserIdAndDegree(educationDetailDto.getUserId(), educationDetailDto.getDegree());
	      if(exists) {
	    	   educationMap.put(ApiResponseEnum.MESSAGE, "This degree is already added for the user.!!");
		        educationMap.put(ApiResponseEnum.SUCCESS, false);
		        return new ResponseEntity<>(educationMap, HttpStatus.OK);
	      }
	        newEducation.setUser(existingUser);
	        Education   savedEducation = educationRepo.save(newEducation);
	        
	       ;

	    if (savedEducation != null) {
	        educationMap.put(ApiResponseEnum.DATA,TO_EDUCATION_DTO_COLLECTION.apply(educationRepo.findByUserId(educationDetailDto.getUserId())) );
	        educationMap.put(ApiResponseEnum.MESSAGE, "Education Details Updated Successfully");
	        educationMap.put(ApiResponseEnum.SUCCESS, true);
	    } else {
	        educationMap.put(ApiResponseEnum.MESSAGE, "Education Details Not Updated!!");
	        educationMap.put(ApiResponseEnum.SUCCESS, false);
	    }

	    return new ResponseEntity<>(educationMap, HttpStatus.OK);
	}
	@Override
	@Transactional
	public ResponseEntity<EnumMap<ApiResponseEnum, Object>> uploadDocument(Long userId, MultipartFile file, String documentType)
	        throws IOException {
	    EnumMap<ApiResponseEnum, Object> fileMap = new EnumMap<>(ApiResponseEnum.class);

	    // Validate user existence
	    UserMaster user = userMasterRepo.findById(userId)
	            .orElseThrow(() -> new ResourceNotFound("User", "id", userId.toString()));

	    // Upload and validate the document
	    Document document = FileUploadUtil.uploadFile(file, "IMAGE/PDF", documentType);
	    document.setUser(user);

	    // Check for an existing document of the same type
	    Document existingDocument = documentRepo.findByUserAndDocumentType(user, documentType);

	    Document savedDocument;
	    if (nonNull(existingDocument)) {
	        // Update the existing document
	        existingDocument.setData(document.getData());
	        existingDocument.setFileName(document.getFileName());
	        existingDocument.setFileType(document.getFileType());
	        savedDocument = documentRepo.save(existingDocument);
	    } else {
	        // Save as a new document
	        savedDocument = documentRepo.save(document);
	    }

	    if (nonNull(savedDocument)) {
	        fileMap.put(ApiResponseEnum.SUCCESS, true);
	        fileMap.put(ApiResponseEnum.MESSAGE, documentType + " uploaded successfully.");
	    } else {
	        fileMap.put(ApiResponseEnum.SUCCESS, false);
	        fileMap.put(ApiResponseEnum.MESSAGE, documentType + " upload failed.");
	    }

	    return new ResponseEntity<>(fileMap, HttpStatus.CREATED);
	}
	@Override
	public ResponseEntity<?> retriveProfileOtherDocs(Long id,String docType) {
		UserMaster user = userMasterRepo.findById(id)
				.orElseThrow(() -> new ResourceNotFound("User", "id", id.toString()));
		Document document = documentRepo.findByUserAndDocumentType(user, docType);
		 return FileUploadUtil.buildDocumentResponse(document);	
	}
	@Override
	public ResponseEntity<EnumMap<ApiResponseEnum, Object>> getUserProfile(Long userId) {
	    EnumMap<ApiResponseEnum, Object> profileMap = new EnumMap<>(ApiResponseEnum.class);
	    log.info("Inside getUserProfile method for userId: {}", userId);

	    UserMaster user = userMasterRepo.findById(userId)
	            .orElseThrow(() -> new ResourceNotFound("User", "id", userId.toString()));

	    UserProfileDto profileDto = TO_USERMASTER_DTO_RESPONSE.apply(user)
	            .orElseThrow(() -> new IllegalStateException("Mapping failed for userId: " + userId));

	    // Enrich with additional nested DTOs
	    Address address = addressRepo.findByUserId(userId);
	    if (nonNull(address)) {
	        profileDto.setAddressDto(TO_ADDRESS_DTO.apply(address).orElse(null));
	    }

	    GuardianDetail guardianDetail = guardianRepo.findByUserId(userId);
	    if (nonNull(guardianDetail)) {
	        profileDto.setGuardianDetailsDto(TO_GUARDIAN_DTO.apply(guardianDetail).orElse(null));
	    }

	    List<Document> documents = documentRepo.findByUser(user);
	    profileDto.setDocumentDtos(documents.stream()
	            .map(doc -> modelMapper.map(doc, DocumentDto.class))
	            .collect(Collectors.toList()));

	    List<Education> educations = educationRepo.findByUserId(userId);
	    profileDto.setEducationDetailsDto(TO_EDUCATION_DTO_COLLECTION.apply(educations));
	    profileDto.setRoles( user.getRoles());
	    profileMap.put(ApiResponseEnum.DATA, profileDto);
	    profileMap.put(ApiResponseEnum.MESSAGE, "User Profile Fetched Successfully");
	    profileMap.put(ApiResponseEnum.SUCCESS, true);

	    return new ResponseEntity<>(profileMap, HttpStatus.OK);
	}


}
