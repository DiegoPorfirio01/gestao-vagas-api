package com.wired.gestao_vagas.modules.candidate.dtos;

import java.util.UUID;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

@Schema(description = "DTO para criação de candidatura a vaga")
public record CreateApplyJobBodyDTO(
        @Schema(description = "ID da vaga", example = "123e4567-e89b-12d3-a456-426614174000") @NotNull UUID jobId,
        @Schema(description = "ID do candidato", example = "123e4567-e89b-12d3-a456-426614174000") @NotNull UUID candidateId) {
}
