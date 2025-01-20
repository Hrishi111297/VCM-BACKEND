package com.bill.tech.controller;

import static com.bill.tech.constants.ApiConstants.AUTH;
import static com.bill.tech.constants.ApiConstants.CAPTCHA;
import static com.bill.tech.constants.ApiConstants.CHANGEPASSWORD;
import static com.bill.tech.constants.ApiConstants.LOGIN;
import static com.bill.tech.constants.ApiConstants.REGISTER;
import static com.bill.tech.constants.ApiConstants.VERIFY_OTP;

import java.util.EnumMap;
import java.util.Map;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bill.tech.authentication.JwtHelper;
import com.bill.tech.authentication.JwtRequest;
import com.bill.tech.authentication.JwtResponse;
import com.bill.tech.exception.CRMException;
import com.bill.tech.marker.CreateValidation;
import com.bill.tech.payload.request.PasswordHistoryDTO;
import com.bill.tech.payload.request.UserMasterDataRequestDto;
import com.bill.tech.service.ChangePasswordService;
import com.bill.tech.service.UserMasterService;
import com.bill.tech.util.AuditAwareUtil;
import com.bill.tech.util.CaptchaService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
/**
 * @author Hrishikesh Mahadik
 * @since 11/01/2025
 * @version 1.0
 *
 */
@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping(AUTH)
@Tag(name = "SecurityController", description = "This Section Gives Us The API Endpoint Related To The SecurityController")

public class Login {
	private final UserDetailsService userDetailsService;
	private final AuthenticationManager authenticationManager;
	private final JwtHelper helper;
	private final ModelMapper modelMapper;
	private final UserMasterService userMasterService;
	private final CaptchaService captchaService;
	private  final ChangePasswordService changePasswordService;


	@PostMapping(LOGIN)
	@Operation(summary = "login", description = "login desc")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "ok"),
			@ApiResponse(responseCode = "401", description = "no authorize"),
			@ApiResponse(responseCode = "201", description = "new user") })
	public ResponseEntity<JwtResponse> login(@Valid @RequestBody JwtRequest request, HttpServletResponse response)
			throws InterruptedException {
		try {
			this.doAuthenticate(request.getUsername(), request.getPassword());
			UserDetails userDetails = userDetailsService.loadUserByUsername(request.getUsername());
			String token = this.helper.generateToken(userDetails);
			JwtResponse jwtResponse = JwtResponse.builder().token(token).username(userDetails.getUsername())
					.userMasterDataRequestDto(modelMapper.map(userDetails, UserMasterDataRequestDto.class)).build();
			return new ResponseEntity<>(jwtResponse, HttpStatus.OK);
		} catch (Exception e) {
			log.error("Error Occured while login.. {}", e.getLocalizedMessage());
			throw new CRMException(e);
		}

	}

	private void doAuthenticate(String username, String password) {

		UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(username,
				password);
		authenticationManager.authenticate(authentication);
	}


	@PostMapping(REGISTER)
	public ResponseEntity<EnumMap<com.bill.tech.enums.ApiResponse, Object>> addUser( @Validated(CreateValidation.class) @RequestBody UserMasterDataRequestDto e) {
		return this.userMasterService.addUser(e);
	}

	@GetMapping(CAPTCHA)
	public Map<String, String> getCaptcha() {
		String[] captcha = captchaService.generateCaptcha();
		return Map.of("text", captcha[0], "image", captcha[1]);
		
	}
	@PostMapping(VERIFY_OTP)
	public  ResponseEntity<EnumMap<com.bill.tech.enums.ApiResponse, Object>> verifyOtp(@PathVariable String emailId) {
		return changePasswordService.verifyOtp(emailId);
	}

@PostMapping(CHANGEPASSWORD)
	public ResponseEntity<EnumMap<com.bill.tech.enums.ApiResponse, Object>> changePassword(@PathVariable String email, @PathVariable Long otp,
			@Valid @RequestBody PasswordHistoryDTO optDto) {
		return changePasswordService.changePassword(email, otp, optDto);
	}



}
