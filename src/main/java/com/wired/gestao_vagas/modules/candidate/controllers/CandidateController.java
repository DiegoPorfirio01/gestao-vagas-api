package com.wired.gestao_vagas.modules.candidate.controllers;

import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.wired.gestao_vagas.modules.candidate.entities.CandidateEntity;
import com.wired.gestao_vagas.modules.candidate.useCases.CreateCandidateUseCase;
import com.wired.gestao_vagas.modules.candidate.useCases.GetPerfilCandidateUseCase;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/candidates")
public class CandidateController {

    @Autowired
    private CreateCandidateUseCase createCandidateUseCase;

    @Autowired
    private GetPerfilCandidateUseCase getPerfilCandidateUseCase;

    // Private && ROLE_CANDIDATE (Get current candidate profile)
    @PreAuthorize("hasRole('ROLE_CANDIDATE')")
    @GetMapping("/me")
    @Tag(name = "Candidates")
    @Operation(summary = "Get current candidate profile")
    @SecurityRequirement(name = "Bearer Authentication")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Candidate profile retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Candidate not found"),
            @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    public ResponseEntity<CandidateEntity> getCurrentPerfilCandidate(HttpServletRequest request) {
        UUID candidateId = UUID.fromString(request.getAttribute("candidate_id").toString());
        var candidate = this.getPerfilCandidateUseCase.execute(candidateId);
        return ResponseEntity.ok(candidate);
    }

    // Public (Create a user candidate)
    @PostMapping
    @Tag(name = "Candidates")
    @Operation(summary = "Create a user candidate")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Candidate created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request body")
    })
    public ResponseEntity<Void> createCandidate(@Valid @RequestBody CandidateEntity candidate) {
        this.createCandidateUseCase.execute(candidate);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
