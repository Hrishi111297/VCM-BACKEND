package com.bill.tech.payload.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EducationDto {

    private Long id;

    @NotEmpty
    @Size(max = 100, message = "Degree must not exceed 100 characters")
    private String degree;

    @NotEmpty
    @Size(max = 100, message = "Field of study must not exceed 100 characters")
    private String fieldOfStudy;

    @NotEmpty
    @Size(max = 200, message = "University name must not exceed 200 characters")
    private String universityName;

    @NotNull(message = "Start year must not be null")
    private Integer startYear;

    @NotNull(message = "End year must not be null")
    private Integer endYear;

    @Size(max = 50, message = "Grade must not exceed 50 characters")
    private String grade;

    @NotNull(message = "IsHighest must not be null")
    private boolean highest;

    @NotNull(message = "User ID must not be null")
    private Long userId;
}
