package com.wired.gestao_vagas.modules.candidate.dtos;

import java.time.LocalDateTime;
import java.util.UUID;

import lombok.Data;
import lombok.NoArgsConstructor;

import com.wired.gestao_vagas.modules.candidate.entities.CandidateEntity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Dados do candidato")
public class GetCandidateResponseDTO {
    @Schema(type = "string", format = "uuid", description = "ID do candidato", example = "123e4567-e89b-12d3-a456-426614174000")
    private UUID id;

    @Schema(type = "string", description = "Nome do candidato", minLength = 3, maxLength = 255, example = "John Doe")
    private String name;

    @Schema(type = "string", description = "Descrição do candidato", example = "John Doe é um desenvolvedor full stack com 5 anos de experiência")
    private String description;

    @Schema(type = "string", description = "Currículo do candidato", example = "https://www.linkedin.com/in/john-doe-1234567890")
    private String curriculum;

    @Schema(type = "string", description = "Username do candidato", minLength = 3, maxLength = 20, pattern = "^[a-zA-Z0-9]+$", example = "john_doe")
    private String username;

    @Schema(type = "string", format = "email", description = "Email do candidato", example = "john.doe@example.com")
    private String email;

    @Schema(type = "string", format = "date-time", description = "Data de criação do candidato", example = "2024-01-01T10:00:00Z")
    private LocalDateTime createdAt;

    public static GetCandidateResponseDTO fromEntity(CandidateEntity candidate) {
        return GetCandidateResponseDTO.builder()
                .id(candidate.getId())
                .name(candidate.getName())
                .username(candidate.getUsername())
                .email(candidate.getEmail())
                .description(candidate.getDescription())
                .curriculum(candidate.getCurriculum())
                .createdAt(candidate.getCreatedAt())
                .build();
    }
}
