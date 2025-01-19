package com.bill.tech.payload.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class KeyValueDto {

    @NotBlank(message = "Key cannot be blank")
    @Size(min = 1, max = 255, message = "Key must be between 1 and 255 characters")
    private String key;

    @NotBlank(message = "Value cannot be blank")
    @Size(min = 1, max = 1000, message = "Value must be between 1 and 1000 characters")
    private String value;
}
