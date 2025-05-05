package com.bill.tech.payload.request;



import java.time.LocalDate;
import java.util.List;

import com.bill.tech.entity.Image;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CourseDto {

    @NotNull(message = "Course name cannot be null")
    @Size(min = 1, max = 100, message = "Course name must be between 1 and 100 characters")
    private String name;

    @NotNull(message = "Description cannot be null")
    @Size(min = 1, message = "Description must be at least 1 character")
    private String description;

    @NotNull(message = "Fee cannot be null")
    @DecimalMin(value = "0.0", inclusive = false, message = "Fee must be greater than 0")
    private Double fee;

    @Size(max = 5000, message = "Syllabus should not exceed 5000 characters")
    private String syllabus;

    @NotNull(message = "Duration cannot be null")
    @Min(value = 1, message = "Duration must be at least 1 month")
    private Integer duration;  // Duration in hours/days or months, depending on your need
   
     // Image data as bytes, can be set in the service layer or controller

    @NotNull(message = "Status cannot be null")
    @Size(min = 3, max = 20, message = "Status must be between 3 and 20 characters")
    private String status;  // To track whether the course is published or in draft

    @NotNull(message = "Level cannot be null")
    @Size(min = 3, max = 50, message = "Level must be between 3 and 50 characters")
    private String level;  // For example: Beginner, Intermediate, Advanced

    @NotNull(message = "Language cannot be null")
    @Size(min = 2, max = 50, message = "Language must be between 2 and 50 characters")
    private String language;  // The language in which the course is taught (e.g., English, Spanish)

    @NotNull(message = "Start date cannot be null")
    @PastOrPresent(message = "Start date must be in the past or present")
    private LocalDate startDate;  // The course start date (using LocalDate)

    @NotNull(message = "End date cannot be null")
    @Future(message = "End date must be in the future")
    private LocalDate endDate;  // The course end date (using LocalDate)

    @NotNull(message = "Category cannot be null")
    private Long categoryId;  // Assuming that the category ID is passed as a reference
  
    private Image images;

   
}
