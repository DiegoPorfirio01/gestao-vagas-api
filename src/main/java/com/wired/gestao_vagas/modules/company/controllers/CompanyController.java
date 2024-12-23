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

import com.wired.gestao_vagas.modules.company.dtos.CompanyPublicDTO;
import com.wired.gestao_vagas.modules.company.entities.CompanyEntity;
import com.wired.gestao_vagas.modules.company.useCases.CreateCompanyUseCase;
import com.wired.gestao_vagas.modules.company.useCases.GetCompaniesUseCase;
import com.wired.gestao_vagas.modules.company.useCases.GetPerfilCompanyUseCase;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
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
    @Tag(name = "Companies")
    @Operation(summary = "Get current company profile")
    @SecurityRequirement(name = "Bearer Authentication")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Company profile retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Company not found"),
            @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    public ResponseEntity<CompanyEntity> getCurrentCompany(HttpServletRequest request) {
        UUID companyId = UUID.fromString(request.getAttribute("company_id").toString());

        return ResponseEntity.ok().body(this.getPerfilCompanyUseCase.execute(companyId));
    }

    // Public
    @GetMapping
    @Tag(name = "Companies")
    @Operation(summary = "List all companies")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Companies listed successfully")
    })
    public List<CompanyPublicDTO> getCompanies() {
        return this.getCompaniesUseCase.execute()
                .stream()
                .map(CompanyPublicDTO::fromEntity)
                .toList();
    }

    // Public
    @PostMapping
    @Tag(name = "Companies")
    @Operation(summary = "Create a company")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Company created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request body")
    })
    public ResponseEntity<Void> createCompany(@Valid @RequestBody CompanyEntity company) {
        this.createCompanyUseCase.execute(company);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
