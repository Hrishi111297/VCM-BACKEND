package com.bill.tech.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bill.tech.entity.OTP;

public interface OtpRepo extends JpaRepository<OTP, Long> {
	OTP findByUserMasterId(long id);

	OTP findByUserMasterEmailIdAndOtpNo(String emailId, Long otpNo);

}
