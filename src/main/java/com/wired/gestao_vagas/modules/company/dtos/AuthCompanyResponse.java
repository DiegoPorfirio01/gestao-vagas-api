package com.wired.gestao_vagas.modules.company.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Response of the company authentication")
public class AuthCompanyResponse {
    @Schema(description = "Token of the company authentication", example = "1234567890")
    private String token;

    @Schema(description = "Expires in of the company authentication", example = "1234567890")
    private Long expiresIn;
}