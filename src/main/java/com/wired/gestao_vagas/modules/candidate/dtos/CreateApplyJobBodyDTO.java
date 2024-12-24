package com.wired.gestao_vagas.modules.candidate.dtos;

import java.util.UUID;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

@Schema(description = "DTO for creating a job application")
public record CreateApplyJobBodyDTO(
                @Schema(description = "Job ID", example = "123e4567-e89b-12d3-a456-426614174000") @NotNull UUID jobId) {
}
