package com.bill.tech.authentication;

import java.io.IOException;
import java.io.PrintWriter;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class EntryPoint implements AuthenticationEntryPoint {

	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException authException) throws IOException, ServletException {
		response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		PrintWriter writer = response.getWriter();
		   response.setContentType("application/json");
		 writer.println("{ \"error\": \"Oops!! Access Denied\", \"message\": \"" 
                 + authException.getMessage() + "\" }");
	
	//	writer.println("Oops!! Access Denied" + authException.getMessage() + authException.getLocalizedMessage() + "");
	}

}
