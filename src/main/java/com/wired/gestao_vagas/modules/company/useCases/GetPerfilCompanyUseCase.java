package com.wired.gestao_vagas.modules.company.useCases;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wired.gestao_vagas.exceptions.NotFoundException;
import com.wired.gestao_vagas.modules.company.repositories.CompanyRepository;
import com.wired.gestao_vagas.modules.company.dtos.GetCurrentCompanyResponseDTO;

@Service
public class GetPerfilCompanyUseCase {
    @Autowired
    private CompanyRepository companyRepository;

    public GetCurrentCompanyResponseDTO execute(UUID companyId) {
        var company = this.companyRepository.findById(companyId)
                .orElseThrow(() -> new NotFoundException("Company not found"));

        return GetCurrentCompanyResponseDTO.fromEntity(company);
    }
}
