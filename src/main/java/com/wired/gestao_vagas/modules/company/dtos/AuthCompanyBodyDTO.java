package com.wired.gestao_vagas.modules.company.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Schema(description = "Body of the company authentication")
public class AuthCompanyBodyDTO {
    @Email(message = "Invalid email")
    @NotBlank(message = "Email is required")
    @Schema(description = "Company email", example = "wired@gmail.com")
    private String email;

    @NotBlank(message = "Password is required")
    @Schema(description = "Company password", example = "12345678")
    private String password;
}