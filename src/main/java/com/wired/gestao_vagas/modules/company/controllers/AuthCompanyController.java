package com.wired.gestao_vagas.modules.company.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import com.wired.gestao_vagas.modules.company.dtos.AuthCompanyBodyDTO;
import com.wired.gestao_vagas.modules.company.dtos.AuthCompanyResponse;
import com.wired.gestao_vagas.modules.company.useCases.AuthCompanyUseCase;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@Tag(name = "Auth")
@RequestMapping("/companies/auth")
public class AuthCompanyController {

    @Autowired
    private AuthCompanyUseCase authCompanyUseCase;

    @PostMapping()
    @Operation(summary = "Authenticate a company")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Company authenticated successfully", content = @Content(schema = @Schema(implementation = AuthCompanyResponse.class))),
            @ApiResponse(responseCode = "400", description = "Invalid request body", content = @Content(schema = @Schema(hidden = true))),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(schema = @Schema(hidden = true)))
    })
    public ResponseEntity<AuthCompanyResponse> auth(@RequestBody AuthCompanyBodyDTO data) {
        var result = authCompanyUseCase.execute(data);

        return ResponseEntity.ok().body(result);
    }
}
