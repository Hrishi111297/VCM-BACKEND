package com.bill.tech.payload.response;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.bill.tech.entity.Role;
import com.bill.tech.payload.request.AddressDto;
import com.bill.tech.payload.request.DocumentDto;
import com.bill.tech.payload.request.EducationDto;
import com.bill.tech.payload.request.GuardianDetailDto;
import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;
@Data
public class UserProfileDto {
	
	private long id;
	
	private String firstName;

	private String middleName;
	
	private String lastName;
	
	private String contactNumber;
	
	private String emailId;
	
	private String gender;
	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	 private LocalDate birthDate;
	
    private String adharNumber;

   
    private String bloodGroup;
    
  
    private AddressDto addressDto;
    private GuardianDetailDto guardianDetailsDto;
    private List<DocumentDto> documentDtos;
    private List<EducationDto> educationDetailsDto;
	

	
	private Set<Role> roles = new HashSet<>();

//	@Override
//	public Collection<? extends GrantedAuthority> getAuthorities() {
//		return this.roles.stream()
//				.map((role) -> new SimpleGrantedAuthority("ROLE_" + role.getName())).collect(Collectors.toList());
//	}


}
