package com.bill.tech.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
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

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "education_detail")
public class Education {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "degree", nullable = false, length = 100)
	private String degree; // e.g., Bachelor's, Master's, Ph.D.

	@Column(name = "field_of_study", nullable = false, length = 100)
	private String fieldOfStudy; // e.g., Computer Science, Physics

	@Column(name = "university_name", nullable = false, length = 200)
	private String universityName; // Name of the university/institution

	@Column(name = "start_year", nullable = false)
	private Integer startYear; // Start year of education

	@Column(name = "end_year")
	private Integer endYear; // End year of education (nullable for ongoing)

	@Column(name = "grade", length = 50)
	private String grade; // e.g., GPA or percentage

	@Column(name = "is_highest", nullable = false)
	private boolean highest; // To mark whether it's the highest education

	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "user_id", nullable = false)
	private UserMaster user; // The user associated with this education
}
