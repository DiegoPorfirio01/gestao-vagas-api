package com.wired.gestao_vagas.modules.candidate.dtos;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "DTO for candidate authentication")
public record AuthCandidateDTO(
        @Schema(description = "Candidate email", example = "john.doe@example.com") String email,
        @Schema(description = "Candidate password", example = "12345678") String password) {
}
