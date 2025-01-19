package com.bill.tech.payload.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddressDto {

	private Long id;
	@NotEmpty( )
	    @Size(max = 10, message = "houseNumber  must not exceed 10 characters"  )
	    private long  houseNumber;
    @NotEmpty( )
    @Size(max = 150, message = "Street must not exceed 150 characters"  )
    private String street;

    @NotEmpty( )
    @Size(max = 100, message = "City must not exceed 100 characters"  )
    private String city;

    @NotEmpty( )
    @Size(max = 100, message = "State must not exceed 100 characters"  )
    private String state;

    @NotEmpty( )
    @Size(max = 10, message = "Postal code must not exceed 10 characters"  )
    private String pincode;

    @NotEmpty( )
    @Size(max = 100, message = "Country must not exceed 100 characters" )
    private String country;
    
    @NotNull(message = "User ID must not be null.")
    private Long userId;
}
