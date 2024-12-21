package com.wired.gestao_vagas.modules.company.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthCompanyDTO {
    @Email
    @NotBlank
    private String email;

    @NotBlank
    private String password;
}
