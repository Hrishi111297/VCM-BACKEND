package com.bill.tech.util;


import static com.bill.tech.constants.RoleConstants.NO_ROLE;
import static com.bill.tech.util.RoleUtil.CHECK_ADMIN;
import static com.bill.tech.util.RoleUtil.CHECK_STUDENT;
import static java.util.Objects.nonNull;
import static org.springframework.security.core.context.SecurityContextHolder.getContext;

import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import com.bill.tech.entity.UserMaster;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class AuditAwareUtil {

	//private final JwtHelper helper;

	public Long getLoggedInStaffId() {
		log.info("inside getLoggedInStaffId method... ");
		if (nonNull(getContext()) && nonNull(getContext().getAuthentication())
				&& nonNull(getContext().getAuthentication().getDetails())) {
			UserMaster details = (UserMaster) getContext().getAuthentication().getPrincipal();
			return details.getId();
		}
		return null;
	}

	public String getLoggedInUserRole() {
		log.info("inside getLoggedInUserRole method... ");
		if (nonNull(getContext()) && nonNull(getContext().getAuthentication())
				&& nonNull(getContext().getAuthentication().getDetails())) {
			UserMaster details = (UserMaster)getContext().getAuthentication().getPrincipal();
			return RoleUtil.getSingleRole(
					details.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()));
		}
		return NO_ROLE;
	}


	public boolean isAdmin() {
		return CHECK_ADMIN.test(getLoggedInUserRole());
	}

	public boolean isStudent() {
		return CHECK_STUDENT.test(getLoggedInUserRole());
	}

}
