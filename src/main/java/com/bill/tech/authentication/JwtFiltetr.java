package com.bill.tech.authentication;

import static com.bill.tech.authentication.AuthenticationUtil.ALLOW_URL;

import java.io.IOException;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

import static com.bill.tech.constants.SecurityConstant.AUTHORIZATION;
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
	            log.info("Skipping JWT filter for URL: {}", request.getServletPath());
	            filterChain.doFilter(request, response);
	            return;
	        }
		String requestHeader = request.getHeader(AUTHORIZATION);
		// Bearer 2352345235sdfrsfgsdfsdf
		log.info(" Header :  {}", requestHeader);
		String username = null;
		String token = null;
		if (requestHeader != null && requestHeader.startsWith("Bearer")) {
			// looking good
			token = requestHeader.substring(7);
			try {

				username = this.jwtHelper.getUsernameFromToken(token);

			} catch (IllegalArgumentException e) {
				logger.info("Illegal Argument while fetching the username !!");
				e.printStackTrace();
			} catch (ExpiredJwtException e) {
				logger.info("Given jwt token is expired !!");
				e.printStackTrace();
				response.setHeader(AUTHORIZATION, null);
			} catch (MalformedJwtException e) {
				logger.info("Some changed has done in token !! Invalid Token");
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();

			}

		} else {
			log.info("Invalid Header Value !! ");
			
	
		}

		//
		if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

			// fetch user detail from username
			UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
			Boolean validateToken = this.jwtHelper.validateToken(token, userDetails);
			if (Boolean.TRUE.equals(validateToken)) {

				// If token is about to expire, refresh it
				Date expirationDate = jwtHelper.getExpirationDateFromToken(token);
				if (expirationDate.before(new Date(System.currentTimeMillis() + 1000 * 60 * 5))) { // 30 minutes before
																									// expiry
					token = jwtHelper.refreshToken(token);
					response.setHeader(AUTHORIZATION, token);
					log.info("Authorization header: " + response.getHeader(AUTHORIZATION));
				}
				// set the authentication
				UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
						userDetails, null, userDetails.getAuthorities());
				authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				SecurityContextHolder.getContext().setAuthentication(authentication);

			} else {
				
				log.info("Validation fails!!");
			}

		}

		filterChain.doFilter(request, response);
	}
}
