package com.wired.gestao_vagas.modules.candidate.controllers;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

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

import jakarta.validation.Valid;

@RestController
@RequestMapping("/candidates")
public class CandidateController {

    @Autowired
    private CreateCandidateUseCase createCandidateUseCase;

    @Autowired
    private GetCandidatesUseCase getCandidatesUseCase;

    @GetMapping()
    public ResponseEntity<List<CandidateEntity>> getCandidates() {
        return ResponseEntity.ok().body(getCandidatesUseCase.execute());
    }

    @PostMapping()
    public ResponseEntity<Object> createCandidate(@Valid @RequestBody CandidateEntity candidate) {
        try {
            this.createCandidateUseCase.execute(candidate);
        } catch (AlreadyExistsException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(true);
    }
}
