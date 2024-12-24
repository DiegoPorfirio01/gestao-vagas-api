package com.wired.gestao_vagas.modules.candidate.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Candidate authentication response")
public class AuthCandidateResponseDTO {
    @Schema(description = "Candidate authentication token", example = "1234567890")
    private String token;

    @Schema(description = "Candidate authentication expires in", example = "1234567890")
    private Long expiresIn;
}
