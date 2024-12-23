package com.wired.gestao_vagas.modules.company.controllers;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wired.gestao_vagas.exceptions.AlreadyExistsException;
import com.wired.gestao_vagas.modules.company.dtos.CompanyPublicDTO;
import com.wired.gestao_vagas.modules.company.entities.CompanyEntity;
import com.wired.gestao_vagas.modules.company.useCases.CreateCompanyUseCase;
import com.wired.gestao_vagas.modules.company.useCases.GetCompaniesUseCase;
import com.wired.gestao_vagas.modules.company.useCases.GetPerfilCompanyUseCase;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/companies")
public class CompanyController {

    @Autowired
    private CreateCompanyUseCase createCompanyUseCase;

    @Autowired
    private GetCompaniesUseCase getCompaniesUseCase;

    @Autowired
    private GetPerfilCompanyUseCase getPerfilCompanyUseCase;

    // Private && ROLE_COMPANY (Get current company profile)
    @PreAuthorize("hasRole('ROLE_COMPANY')")
    @GetMapping("/me")
    public ResponseEntity<CompanyEntity> getCurrentCompany(HttpServletRequest request) {
        UUID companyId = UUID.fromString(request.getAttribute("company_id").toString());

        return ResponseEntity.ok().body(this.getPerfilCompanyUseCase.execute(companyId));
    }

    // Public
    @GetMapping
    public List<CompanyPublicDTO> getCompanies() {
        return this.getCompaniesUseCase.execute()
                .stream()
                .map(CompanyPublicDTO::fromEntity)
                .toList();
    }

    // Public
    @PostMapping
    public ResponseEntity<Void> createCompany(@Valid @RequestBody CompanyEntity company) {
        this.createCompanyUseCase.execute(company);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
