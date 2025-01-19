package com.bill.tech.payload.request;

import com.bill.tech.marker.CreateValidation;
import com.bill.tech.marker.UpdateValidation;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryDto {

    
    private Long id;

    @NotBlank(  message = "Category name cannot be blank")
    @Size(max = 100,   message = "Category name must not exceed 100 characters")
    private String name;

    @Size(max = 255,   message = "Description must not exceed 255 characters")
    private String description;
}
