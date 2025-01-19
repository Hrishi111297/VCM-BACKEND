package com.bill.tech.payload.request;



import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DocumentDto {

    private Long id;

    @NotBlank(message = "File name must not be blank.")
    @Size(max = 255, message = "File name must not exceed 255 characters.")
    private String fileName;

    @NotBlank(message = "File type must not be blank.")
    @Pattern(regexp = "^(image/.+|application/pdf|text/.+)$", 
             message = "File type must be a valid MIME type (e.g., image/png, application/pdf).")
    private String fileType;

    @NotNull(message = "File data must not be null.")
    private byte[] data;

    @NotNull(message = "User ID must not be null.")
    private Long userId; // Used to map the document to a specific user
}
