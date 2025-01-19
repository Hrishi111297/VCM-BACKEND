
package com.bill.tech.constants;

public class RoleConstants {

	private RoleConstants() {

	}

	public static final String VCM_ADMIN = "ADMIN";

	public static final String VCM_STUDENT = "STUDENT";

	public static final String VCM_SUPER_ADMIN = "SUPER_ADMIN";

	public static final String HAS_AUTORITY = "hasAuthority('";

	public static final String HAS_AUTHORITY_CLOSER = "')";

	public static final String CHECK_ALL_ACCESS = HAS_AUTORITY + VCM_ADMIN + HAS_AUTHORITY_CLOSER + " || "
			+ HAS_AUTORITY + VCM_STUDENT + HAS_AUTHORITY_CLOSER + " || " + HAS_AUTORITY + VCM_SUPER_ADMIN
			+ HAS_AUTHORITY_CLOSER;

	public static final String CHECK_ADMIN_STUDENT_ACCESS = HAS_AUTORITY + VCM_ADMIN + HAS_AUTHORITY_CLOSER + " || "
			+ HAS_AUTORITY + VCM_STUDENT + HAS_AUTHORITY_CLOSER;

	public static final String CHECK_ADMIN_ACCESS = HAS_AUTORITY + VCM_ADMIN + HAS_AUTHORITY_CLOSER;

	public static final String CHECK_STUDENT_ACCESS = HAS_AUTORITY + VCM_STUDENT + HAS_AUTHORITY_CLOSER;

	public static final String CHECK_SUPER_ADMIN_ACCESS = HAS_AUTORITY + VCM_SUPER_ADMIN + HAS_AUTHORITY_CLOSER;

	public static final String NO_ROLE = "Don't Have Role";
}
