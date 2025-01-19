/**
 * 
 */
package com.bill.tech.entity;

import org.hibernate.annotations.Where;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@SuppressWarnings("deprecation")
@Entity
@Getter
@Setter
@Table(name = "otp")
@Where(clause = "deleted_by is null")
public class OTP extends Auditable {

	private static final long serialVersionUID = -8731411545705783869L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "otp_id")
	private long otpID;

	@Column(name = "email_id")
	private String email;

	@Column(name = "otp")
	private Long otpNo;

	@OneToOne(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
	@JoinColumn(name = "id", referencedColumnName = "id")
	private UserMaster userMaster;

}
