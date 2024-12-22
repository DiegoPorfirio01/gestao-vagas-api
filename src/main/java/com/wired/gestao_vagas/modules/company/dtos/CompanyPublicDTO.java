package com.wired.gestao_vagas.modules.company.dtos;

import lombok.Data;
import lombok.NoArgsConstructor;

import com.wired.gestao_vagas.modules.company.entities.CompanyEntity;

import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CompanyPublicDTO {
    private String id;
    private String name;
    private String description;
    private String website;

    public static CompanyPublicDTO fromEntity(CompanyEntity company) {
        return new CompanyPublicDTO(company.getId().toString(), company.getName(), company.getDescription(),
                company.getWebsite());
    }
}
