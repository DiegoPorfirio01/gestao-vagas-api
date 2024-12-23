package com.wired.gestao_vagas.modules.company.dtos;

import java.time.LocalDateTime;
import java.util.UUID;

import com.wired.gestao_vagas.modules.company.entities.CompanyEntity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Data of the current company")
public class GetCurrentCompanyResponseDTO {
    @Schema(description = "Company ID", example = "3fa85f64-5717-4562-b3fc-2c963f66afa6")
    private UUID id;

    @Schema(description = "Nome da empresa", example = "Wired")
    private String name;

    @Schema(description = "Email da empresa", example = "wired@gmail.com")
    private String email;

    @Schema(description = "Website da empresa", example = "www.wired.com")
    private String website;

    @Schema(description = "Descrição da empresa", example = "A Wired é uma empresa de tecnologia que desenvolve soluções para o mercado")
    private String description;

    @Schema(description = "Data de criação da empresa", example = "2024-01-01")
    private LocalDateTime createdAt;

    public static GetCurrentCompanyResponseDTO fromEntity(CompanyEntity company) {
        return GetCurrentCompanyResponseDTO.builder()
                .id(company.getId())
                .name(company.getName())
                .email(company.getEmail())
                .website(company.getWebsite())
                .description(company.getDescription())
                .createdAt(company.getCreatedAt())
                .build();
    }
}
