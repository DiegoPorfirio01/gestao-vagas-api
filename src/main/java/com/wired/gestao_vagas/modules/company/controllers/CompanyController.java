package com.wired.gestao_vagas.modules.company.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

import jakarta.validation.Valid;

@RestController
@RequestMapping("/companies")
public class CompanyController {

    @Autowired
    private CreateCompanyUseCase createCompanyUseCase;

    @Autowired
    private GetCompaniesUseCase getCompaniesUseCase;

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
    public ResponseEntity<Object> createCompany(@Valid @RequestBody CompanyEntity company) {
        try {
            this.createCompanyUseCase.execute(company);
        } catch (AlreadyExistsException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(true);
    }
}
