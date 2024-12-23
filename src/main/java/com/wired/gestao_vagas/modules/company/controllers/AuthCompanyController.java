package com.wired.gestao_vagas.modules.company.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wired.gestao_vagas.modules.company.dtos.AuthCompanyDTO;
import com.wired.gestao_vagas.modules.company.dtos.AuthCompanyResponse;
import com.wired.gestao_vagas.modules.company.useCases.AuthCompanyUseCase;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/companies/auth")
public class AuthCompanyController {

    @Autowired
    private AuthCompanyUseCase authCompanyUseCase;

    @PostMapping()
    @Tag(name = "Companies")
    @Operation(summary = "Authenticate a company")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Company authenticated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request body"),
            @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    public ResponseEntity<AuthCompanyResponse> auth(@RequestBody AuthCompanyDTO data) {
        var result = authCompanyUseCase.execute(data);

        return ResponseEntity.ok().body(result);
    }
}
