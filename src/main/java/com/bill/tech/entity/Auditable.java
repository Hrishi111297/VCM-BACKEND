package com.bill.tech.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
@MappedSuperclass
public class Auditable implements Serializable {
	/**
	 * 
	 */
	private static final Long serialVersionUID = -4307510146029602159L;

	@Column(name = "created_by", nullable = true, updatable = true)
	private Long createdBy;

	@Column(name = "created_date", nullable = true, updatable = true, columnDefinition = "TIMESTAMP")
	private LocalDateTime createdDate;

	@Column(name = "updated_by")
	private Long updatedBy;

	@Column(name = "updated_date", columnDefinition = "TIMESTAMP")
	private LocalDateTime updatedDate;

	@Column(name = "deleted_by")
	protected Long deletedBy;

	@Column(name = "deleted_date", columnDefinition = "TIMESTAMP")
	private LocalDateTime deletedDate;

	@PrePersist
	public void beforePersist() {
		/*
		 * HttpServletRequest request = ((ServletRequestAttributes)
		 * RequestContextHolder.getRequestAttributes()) .getRequest(); String staffId =
		 * (String) request.getAttribute("staffId"); if (staffId != null) {
		 * this.createdBy = Integer.parseInt(staffId); }
		 */
		this.createdBy = 1l;

		this.createdDate = LocalDateTime.now();
	}

	@PreUpdate
	public void beforUpdate() {
		/*
		 * HttpServletRequest request = ((ServletRequestAttributes)
		 * RequestContextHolder.getRequestAttributes()) .getRequest(); String staffId =
		 * (String) request.getAttribute("staffId"); if (staffId != null) {
		 * this.updatedBy = Integer.parseInt(staffId); }
		 */
		this.updatedBy = 1l;
		this.updatedDate = LocalDateTime.now();
	}

	// @PreRemove
	public void beforDelete() {
		/*
		 * HttpServletRequest request = ((ServletRequestAttributes)
		 * RequestContextHolder.getRequestAttributes()) .getRequest(); String staffId =
		 * (String) request.getAttribute("staffId"); if (staffId != null) {
		 * this.deletedBy = Integer.parseInt(staffId); }
		 */
		this.deletedBy = 1l;
		this.deletedDate = LocalDateTime.now();

	}

}
