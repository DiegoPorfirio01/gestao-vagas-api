package com.wired.gestao_vagas.modules.company.dtos;

import org.hibernate.validator.constraints.Length;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Body of the company creation")
public class CreateCompanyBodyDTO {
    @NotBlank(message = "Name is required")
    @Length(min = 3, max = 255, message = "Name must be between 3 and 255 characters")
    @Schema(description = "Company name", example = "Wired")
    private String name;

    @Email(message = "Invalid email")
    @NotBlank(message = "Email is required")
    @Column(unique = true)
    @Schema(description = "Company email", example = "wired@gmail.com")
    private String email;

    @Pattern(regexp = "^www\\.[a-zA-Z0-9-]+\\.(com|com\\.br)$", message = "Website must be in the format www.domain.com or www.domain.com.br")
    @NotBlank(message = "Website is required")
    @Schema(description = "Company website", example = "www.wired.com")
    private String website;

    @Length(min = 8, message = "Password must be at least 8 characters")
    @NotBlank(message = "Password is required")
    @Schema(description = "Company password", example = "12345678")
    private String password;

    @Schema(description = "Company description", example = "A Wired is a technology company that develops solutions for the market")
    private String description;
}
