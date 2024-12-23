package com.wired.gestao_vagas.modules.candidate.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wired.gestao_vagas.modules.candidate.dtos.AuthCandidateDTO;
import com.wired.gestao_vagas.modules.candidate.dtos.AuthCandidateResponseDTO;
import com.wired.gestao_vagas.modules.candidate.useCases.AuthCandidateUseCase;

@RestController
@RequestMapping("/candidates")
public class AuthCandidateController {

    @Autowired
    private AuthCandidateUseCase authCandidateUseCase;

    @PostMapping("/auth")
    public ResponseEntity<AuthCandidateResponseDTO> auth(@RequestBody AuthCandidateDTO data) {
        AuthCandidateResponseDTO authCandidateResponse = authCandidateUseCase.execute(data);

        return ResponseEntity.ok().body(authCandidateResponse);
    }

}
