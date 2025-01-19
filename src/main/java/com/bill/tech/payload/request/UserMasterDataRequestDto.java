package com.bill.tech.payload.request;

import java.time.LocalDate;
import java.util.Date;
import java.util.Set;

import com.bill.tech.marker.CreateValidation;
import com.bill.tech.marker.UpdateValidation;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserMasterDataRequestDto {
	@NotNull(groups = {UpdateValidation.class})
	private Long id;

	    @NotEmpty(groups = {CreateValidation.class, UpdateValidation.class})
	    @Size(max = 100, message = "Please enter a valid first Name", groups = {CreateValidation.class, UpdateValidation.class})
	    private String firstName;

	    @NotEmpty(groups = {CreateValidation.class, UpdateValidation.class})
	    @Size(max = 100, message = "Please enter a valid middle name", groups = {CreateValidation.class, UpdateValidation.class})
	    private String middleName;

	    @NotEmpty(groups = {CreateValidation.class, UpdateValidation.class})
	    @Size(max = 100, message = "Please enter a valid last name", groups = {CreateValidation.class, UpdateValidation.class})
	    private String lastName;
	    
	    @NotEmpty(groups = {CreateValidation.class, UpdateValidation.class})
	    @NotNull(groups = {CreateValidation.class, UpdateValidation.class})
	    @Size(max = 10, message = "Please enter a valid gender", groups = {CreateValidation.class, UpdateValidation.class})
	    private String gender;

	    @NotNull(message = "Birthdate cannot be null" ,groups = {CreateValidation.class, UpdateValidation.class})
	     @Past(message = "Birthdate must be a date in the past", groups = { UpdateValidation.class})
	    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	    private LocalDate birthDate;
	    
	    @NotNull(message = "Aadhar number cannot be null",groups = { UpdateValidation.class})
	    @Pattern(regexp = "\\d{12}", message = "Aadhar number must be a 12-digit numeric value",groups = { UpdateValidation.class})
	    private String adharNumber;

	    // Blood Group Validation
	    @NotNull(message = "Blood group cannot be null",groups = { UpdateValidation.class})
	    @Size(max = 5, message = "Blood group must not exceed 5 characters",groups = { UpdateValidation.class})
	    private String bloodGroup;
	    @Email(message = "Please enter a valid email id", groups = {CreateValidation.class, UpdateValidation.class})
	    @NotEmpty(groups = {CreateValidation.class, UpdateValidation.class})
	    private String emailId;

	    @NotEmpty(groups = {CreateValidation.class, UpdateValidation.class})
	    @Pattern(regexp = "\\d{10}", message = "Please enter a valid contact number", groups = {CreateValidation.class, UpdateValidation.class})
	    private String contactNumber;

	    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	    @NotNull(groups = {CreateValidation.class})
	    @NotEmpty(groups = {CreateValidation.class})
	    @Size(min = 8, message = "Password must be at least 8 characters long", groups = {CreateValidation.class})
	    @Pattern(
	        regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=]).*$",
	        message = "Password must contain at least one digit, one lowercase letter, one uppercase letter, and one special character",
	        groups = {CreateValidation.class}
	    )
	    private String password;
	    
	    
	    
	    
	    @NotNull(groups = {UpdateValidation.class})
	    @NotEmpty(groups = {UpdateValidation.class}, message = "At least one role is required for update")
	    @Size(min = 1, message = "At least one role must be provided", groups = {CreateValidation.class, UpdateValidation.class})
	    private Set<Long> roleIds; // IDs of the roles to assign
}