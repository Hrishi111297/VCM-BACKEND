package com.bill.tech.payload.request;

import java.time.LocalDate;
import java.util.Set;

import com.bill.tech.entity.Attendance;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class BatchDto {

	private Long id;
	@NotBlank(message = "Batch name cannot be blank")
	@Size(max = 100, message = "Batch name must not exceed 100 characters")
	private String name;
	@NotNull(message = "Start date cannot be null")
	private LocalDate startDate;
	@NotNull(message = "End date cannot be null")
	private LocalDate endDate;
	@NotBlank(message = "duration cannot be blank")
	private String duration;

	private boolean isOpen = true;

	@NotNull(message = "courseId cannot be null")
	private Long courseId;

	private Set<Long> teacherIds;

	private Set<Long> students;
	
	private Set<Attendance> attendenceList;

}
