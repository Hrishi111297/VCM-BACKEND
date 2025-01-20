package com.bill.tech.util;
import static com.bill.tech.constants.RoleConstants.NO_ROLE;
import static com.bill.tech.constants.RoleConstants.VCM_ADMIN;
import static com.bill.tech.constants.RoleConstants.VCM_STUDENT;
import static com.bill.tech.constants.RoleConstants.VCM_TEACHER;
import static java.util.Objects.isNull;
import static lombok.AccessLevel.PRIVATE;

import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.function.Supplier;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@NoArgsConstructor(access = PRIVATE)
@Slf4j
public class RoleUtil {

	/**
	 * This Predicate return true if it contains the Role.
	 * 
	 * @since version 1.0
	 */
	public static final Predicate<String> ALLOW_ROLES = s -> {
		if (isNull(s))
			return false;
		return RoleUtil.APP_ROLES.get().stream().anyMatch(s::equalsIgnoreCase);
	};

	public static final Supplier<List<String>> APP_ROLES = () -> Arrays.asList(VCM_ADMIN, VCM_STUDENT);

//	public static final UnaryOperator<String> GET_ROLE = s -> {
//		if (CRM_ADMIN.equalsIgnoreCase(s))
//			return CRM_ADMIN;
//		else if (CRM_USER.equalsIgnoreCase(s))
//			return CRM_USER;
//		return NO_ROLE;
//	};

	/**
	 * This Predicate return true if it contains the CRM Admin Role.
	 * 
	 * @since version 1.0
	 */
	public static final Predicate<String> CHECK_ADMIN = s -> {
		if (isNull(s))
			return false;
		return VCM_ADMIN.equalsIgnoreCase(s.split("_")[1]);
	};
	/**
	 * This Predicate return true if it contains the CRM User Role.
	 * 
	 * @since version 1.0
	 */
	public static final Predicate<String> CHECK_STUDENT = s -> {
		if (isNull(s))
			return false;
		return VCM_STUDENT.equalsIgnoreCase(s.split("_")[1]);
	};

	public static final Predicate<String> CHECK_TEACHER = s -> {
		if (isNull(s))
			return false;
		return VCM_TEACHER.equalsIgnoreCase(s);
	};
	public static final String getSingleRole(List<String> roles) {
		log.info("inside the getSingleRole method...{}");
		return roles.stream().filter(CHECK_ADMIN).findFirst()
				.orElse(roles.stream().filter(CHECK_STUDENT).findFirst().orElse(NO_ROLE));
	}
}
