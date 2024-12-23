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
    @Email(message = "Email inválido")
    @NotBlank(message = "Email é obrigatório")
    @Schema(description = "Email of the company", example = "wired@gmail.com")
    private String email;

    @NotBlank(message = "Senha é obrigatória")
    @Schema(description = "Password of the company", example = "12345678")
    private String password;
}