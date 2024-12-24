package com.wired.gestao_vagas.modules.company.dtos;

import java.time.LocalDateTime;
import java.util.UUID;

import lombok.Data;
import lombok.NoArgsConstructor;

import com.wired.gestao_vagas.modules.company.entities.CompanyEntity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Company data")
public class GetCompanyResponseDTO {
    @Schema(description = "Company ID", example = "1")
    private UUID id;

    @Schema(description = "Company name", example = "Wired")
    private String name;

    @Schema(description = "Company description", example = "A Wired is a technology company that develops solutions for the market")
    private String description;

    @Schema(description = "Company website", example = "www.wired.com")
    private String website;

    @Schema(description = "Company creation date", example = "2024-01-01")
    private LocalDateTime createdAt;

    public static GetCompanyResponseDTO fromEntity(CompanyEntity company) {
        return GetCompanyResponseDTO.builder()
                .id(company.getId())
                .name(company.getName())
                .description(company.getDescription())
                .website(company.getWebsite())
                .createdAt(company.getCreatedAt())
                .build();
    }
}
