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

import com.wired.gestao_vagas.modules.candidate.entities.CandidateEntity;
import com.wired.gestao_vagas.modules.candidate.useCases.CreateCandidateUseCase;
import com.wired.gestao_vagas.modules.candidate.useCases.GetPerfilCandidateUseCase;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@RestController
public class CandidateController {

    @Autowired
    private CreateCandidateUseCase createCandidateUseCase;

    @Autowired
    private GetPerfilCandidateUseCase getPerfilCandidateUseCase;

    // Private && ROLE_CANDIDATE (Get current candidate profile)
    @PreAuthorize("hasRole('ROLE_CANDIDATE')")
    @GetMapping("/candidates/me")
    public ResponseEntity<CandidateEntity> getCurrentPerfilCandidate(HttpServletRequest request) {
        UUID candidateId = UUID.fromString(request.getAttribute("candidate_id").toString());
        var candidate = this.getPerfilCandidateUseCase.execute(candidateId);
        return ResponseEntity.ok(candidate);
    }

    // Public (Create a user candidate)
    @PostMapping("/candidates")
    public ResponseEntity<Void> createCandidate(@Valid @RequestBody CandidateEntity candidate) {
        this.createCandidateUseCase.execute(candidate);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
