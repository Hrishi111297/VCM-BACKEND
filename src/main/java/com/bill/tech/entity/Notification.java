package com.bill.tech.entity;

import java.util.Date;

import org.hibernate.annotations.Where;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
@Table(name = "notification")
@Where(clause = "deleted_by is null")
public class Notification extends Auditable {
	
	
	private static final long serialVersionUID = 7711336781296834503L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String title;

	private String message;

	private Date dateSent;

	@ManyToOne
	@JoinColumn(name = "sent_to")
	private Student student; // If null, notification is for all students
}
