package com.bill.tech.authentication;

import static java.util.Objects.isNull;
import static lombok.AccessLevel.PRIVATE;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.function.Supplier;

import lombok.NoArgsConstructor;

@NoArgsConstructor(access = PRIVATE)
public class AuthenticationUtil {

	public static final String[] OPEN_REQUEST1= {
		    "/api/v1/auth/.*", 
		    "/v3/api-docs/.*", 
		    "/swagger-resources/.*", 
		    "/swagger-ui/.*", 
		    "/webjars/.*", 
		    "/ws/.*", 
		    "/notifications/.*"
		};

	public static final String[] PUBLIC_URLS = { "/crmnotification", "/crmnotification/**", "/api/v1/auth/**",
			"/v3/api-docs", "/v2/api-docs", "/swagger-resources/**", "/swagger-ui/**", "/webjars/**" };
	public static final String[] ADMIN_URLS = { "/api/v1/admin/**" };
	public static final String[] USER_URLS = { "/api/v1/user/**" };
	
	

	/**
	 * This Predicate return true if passed API URL is public.
	 * 
	 * @since version 1.0
	 */
	public static final Predicate<String> ALLOW_URL = s -> {
		if (isNull(s))
			return false;
		else if (s.contains("swagger") || s.contains("webjars"))
			return true;
		return Arrays.stream(OPEN_REQUEST1).anyMatch(s::matches);
	};

}