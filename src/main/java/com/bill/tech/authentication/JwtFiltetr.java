package com.bill.tech.authentication;

import static com.bill.tech.authentication.AuthenticationUtil.ALLOW_URL;
import static com.bill.tech.constants.SecurityConstant.*;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import static java.util.Objects.*;
import static com.bill.tech.constants.CommonConstants.*;

@Component
@Slf4j
public class JwtFiltetr extends OncePerRequestFilter {

	@Autowired
	private JwtHelper jwtHelper;

	@Autowired
	private UserDetailsService userDetailsService;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		if (ALLOW_URL.test(request.getServletPath())) {
			log.info(SKIP_JWT_FILTER, request.getServletPath());
			filterChain.doFilter(request, response);
			return;
		}
		try {
			String requestHeader = request.getHeader(AUTHORIZATION);
			log.info(HEADER, requestHeader);
			String username = null;
			String token = null;
			if (nonNull(requestHeader) && requestHeader.startsWith(BEARER)) {
				token = requestHeader.substring(7);

				username = this.jwtHelper.getUsernameFromToken(token);

			} else {
				log.info(INVALID_HEADER);

			}
			if (nonNull(username) && isNull(SecurityContextHolder.getContext().getAuthentication())) {

				UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
				Boolean validateToken = this.jwtHelper.validateToken(token, userDetails);
				if (Boolean.TRUE.equals(validateToken)) {
					Date expirationDate = jwtHelper.getExpirationDateFromToken(token);
					if (expirationDate.before(new Date(System.currentTimeMillis() + 1000 * 60 * 5))) {
						token = jwtHelper.refreshToken(token);
						response.setHeader(AUTHORIZATION, token);
						log.info(HEADER + response.getHeader(AUTHORIZATION));
					}
					UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
							userDetails, null, userDetails.getAuthorities());
					authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
					SecurityContextHolder.getContext().setAuthentication(authentication);

				} else {
					log.info(VALIDATION_FAILED);
				}

			}

			filterChain.doFilter(request, response);
		} catch (ExpiredJwtException e) {
			log.error(TOKEN_EXPIRED);
			setErrorResponse(HttpServletResponse.SC_UNAUTHORIZED, response, TOKEN_EXPIRED);
		} catch (MalformedJwtException e) {
			log.error(INVALID_JWT_TOKEN);
			setErrorResponse(HttpServletResponse.SC_BAD_REQUEST, response, INVALID_JWT_TOKEN);
		} catch (SignatureException e) {
			log.error(INVALID_SIGNATURE);
			setErrorResponse(HttpServletResponse.SC_BAD_REQUEST, response, INVALID_SIGNATURE);
		} catch (Exception e) {
			log.error(UNEXPECTED_ERROR, e);
			setErrorResponse(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, response,
					UNEXPECTED_ERROR + e.getLocalizedMessage());
		}

	}

	private void setErrorResponse(int status, HttpServletResponse response, String message) {
		String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern(REVERSE_DATE_TIME));
		response.setStatus(status);
		response.setContentType(JSON_CONTENT_TYPE);
		try {
			String json = String.format(JSON_OBJECT, timestamp, status, message);
			response.getWriter().write(json);
		} catch (IOException e) {
			log.error(RESPONSE_ERROR, e);
		}
	}
}
