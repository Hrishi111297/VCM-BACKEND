package com.bill.tech.constants;

import lombok.NoArgsConstructor;
import static lombok.AccessLevel.PRIVATE;
@NoArgsConstructor(access = PRIVATE)
public class SecurityConstant {
	public static final String RSA = "RSA";
	public static final String AUTHORIZATION = "Authorization";
	public static final String BEARER = "Bearer";
	public static final String SKIP_JWT_FILTER = "Skipping JWT filter for URL: {}";
	public static final String HEADER= "Header:{}";
	public static final String JSON_OBJECT ="{\"timestamp\": \"%s\", \"status\": %d, \"message\": \"%s\"}";




//JWT Messages
	public static final String INVALID_HEADER = "Invalid Header Value !!";
	public static final String VALIDATION_FAILED = "Validation failed !!";
	public static final String TOKEN_EXPIRED = "JWT token has expired , Please login again.!!";
	public static final String INVALID_JWT_TOKEN = "Invalid JWT token !!";
	public static final String INVALID_SIGNATURE = "IInvalid JWT Signature!!";
	public static final String UNEXPECTED_ERROR= "An unexpected error occurred during token validation!!";
	public static final String JSON_CONTENT_TYPE = "application/json";
	public static final String RESPONSE_ERROR ="Error writing the response";
	public static final String ACCESS_DENIED ="Error writing the response";
}
