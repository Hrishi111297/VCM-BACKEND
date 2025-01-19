package com.bill.tech.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.bill.tech.entity.UserMaster;
import com.bill.tech.exception.ResourceNotFound;
import com.bill.tech.repository.UserMasterRepo;

@Service
public class CustomUserDetailService implements UserDetailsService {
	@Autowired
	UserMasterRepo userMasterRepo;

	@Override
	public UserDetails loadUserByUsername(String emailId) throws UsernameNotFoundException {
		UserMaster student = this.userMasterRepo.findByEmailId(emailId)
				.orElseThrow(() -> new ResourceNotFound("User", "username", emailId));
		return student;
	}

}
