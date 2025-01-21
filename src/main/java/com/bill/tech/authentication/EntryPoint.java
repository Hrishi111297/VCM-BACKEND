package com.bill.tech.authentication;

import static com.bill.tech.constants.CommonConstants.REVERSE_DATE_TIME;
import static com.bill.tech.constants.SecurityConstant.JSON_OBJECT;

import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import static com.bill.tech.constants.SecurityConstant.*;

@Component
public class EntryPoint implements AuthenticationEntryPoint {

	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException authException) throws IOException, ServletException {
		String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern(REVERSE_DATE_TIME));
		response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		PrintWriter writer = response.getWriter();
		   response.setContentType(JSON_CONTENT_TYPE);
		   String json = String.format(JSON_OBJECT, timestamp, HttpServletResponse.SC_UNAUTHORIZED, ACCESS_DENIED+authException.getLocalizedMessage());
		 writer.println(json);
	}

}
