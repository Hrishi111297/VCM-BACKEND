package com.bill.tech.entity;

import org.hibernate.annotations.Where;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@SuppressWarnings("deprecation")
@Entity

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "admin")
@Where(clause = "deleted_by is null")
public class Admin extends Auditable{

	private static final long serialVersionUID = 6478429803638541495L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@OneToOne
	@MapsId
	@JoinColumn(name = "user_id")
	private UserMaster user;
	
	private boolean isSuperAdmin = false;

	private String privileges;
	

}
