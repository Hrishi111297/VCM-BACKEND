package com.bill.tech.entity;

import java.util.Date;
import java.util.List;

import org.hibernate.annotations.Where;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
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
@Table(name = "batch")
@Where(clause = "deleted_by is null")
public class Batch extends Auditable {

	private static final long serialVersionUID = -4640760640680721912L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String name;
	
	private Date startDate;
	
	private Date endDate;
	
	private String timings;

	@ManyToOne
	@JoinColumn(name = "course_id")
	private Course course;

	@ManyToMany
	@JoinTable(name = "batch_teachers", joinColumns = @JoinColumn(name = "batch_id"), inverseJoinColumns = @JoinColumn(name = "teacher_id"))
	private List<Teacher> teachers;

	@OneToMany(mappedBy = "batch")
	private List<Attendance> attendanceRecords;
}
