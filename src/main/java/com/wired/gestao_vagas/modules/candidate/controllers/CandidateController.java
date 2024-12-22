package com.wired.gestao_vagas.modules.candidate.controllers;

import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.wired.gestao_vagas.exceptions.AlreadyExistsException;
import com.wired.gestao_vagas.modules.candidate.entities.CandidateEntity;
import com.wired.gestao_vagas.modules.candidate.useCases.CreateCandidateUseCase;
import com.wired.gestao_vagas.modules.candidate.useCases.GetCandidatesUseCase;
import com.wired.gestao_vagas.modules.candidate.useCases.GetPerfilCandidateUseCase;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@RestController
public class CandidateController {

    @Autowired
    private CreateCandidateUseCase createCandidateUseCase;

    @Autowired
    private GetPerfilCandidateUseCase getPerfilCandidateUseCase;

    // Private (Get current candidate profile)
    @GetMapping("/candidates/me")
    public ResponseEntity<CandidateEntity> getCurrentPerfilCandidate(HttpServletRequest request) {
        String candidateId = request.getAttribute("candidate_id").toString();

        return ResponseEntity.ok().body(getPerfilCandidateUseCase.execute(candidateId));
    }

    // Public (Create a user candidate)
    @PostMapping("/candidates")
    public ResponseEntity<Object> createCandidate(@Valid @RequestBody CandidateEntity candidate) {
        try {
            this.createCandidateUseCase.execute(candidate);
        } catch (AlreadyExistsException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(true);
    }
}
