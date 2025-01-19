package com.bill.tech.authentication;

import java.util.Arrays;
import java.util.Date;
import static com.bill.tech.authentication.AuthenticationUtil.ALLOW_URL;

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
import io.jsonwebtoken.io.IOException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtFiltetr extends OncePerRequestFilter {

	private Logger logger = LoggerFactory.getLogger(OncePerRequestFilter.class);
	@Autowired
	private JwtHelper jwtHelper;

	@Autowired
	private UserDetailsService userDetailsService;


	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException, java.io.IOException {
			if (ALLOW_URL.test(request.getServletPath())) {
	            logger.info("Skipping JWT filter for URL: {}", request.getServletPath());
	            filterChain.doFilter(request, response);
	            return;
	        }
		String requestHeader = request.getHeader("Authorization");
		// Bearer 2352345235sdfrsfgsdfsdf
		logger.info(" Header :  {}", requestHeader);
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
				response.setHeader("Authorization", null);
			} catch (MalformedJwtException e) {
				logger.info("Some changed has done in token !! Invalid Token");
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();

			}

		} else {
			logger.info("Invalid Header Value !! ");
			//throw new CRMException("Authorization header missing or invalid",
                //    new ApiError(false, "Invalid header value", HttpStatus.BAD_REQUEST));
	
		}

		//
		if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

			// fetch user detail from username
			UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
			Boolean validateToken = this.jwtHelper.validateToken(token, userDetails);
			if (validateToken) {

				// If token is about to expire, refresh it
				Date expirationDate = jwtHelper.getExpirationDateFromToken(token);
				if (expirationDate.before(new Date(System.currentTimeMillis() + 1000 * 60 * 5))) { // 30 minutes before
																									// expiry
					token = jwtHelper.refreshToken(token);
					response.setHeader("Authorization", token);
					logger.info("Authorization header: " + response.getHeader("Authorization"));
				}
				// set the authentication
				UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
						userDetails, null, userDetails.getAuthorities());
				authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				SecurityContextHolder.getContext().setAuthentication(authentication);

			} else {
				 // throw new CRMException("Token validation failed",
	                     //   new ApiError(false, "Invalid token", HttpStatus.UNAUTHORIZED));
				logger.info("Validation fails!!");
			}

		}

		filterChain.doFilter(request, response);
	}
}
