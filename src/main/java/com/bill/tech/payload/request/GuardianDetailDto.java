package com.bill.tech.payload.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GuardianDetailDto {
	
    @NotEmpty 
    @Size(max = 100, message = "Guardian name must not exceed 100 characters")
    private String name;

    @NotEmpty 
    @Size(max = 15, message = "Guardian contact number must not exceed 15 characters")
    private String contactNumber;

    @NotEmpty 
    @Size(max = 50, message = "Relation must not exceed 50 characters")
    private String relationship;
    
    @NotNull(message = "User ID must not be null.")
    private Long userId;

	
}
