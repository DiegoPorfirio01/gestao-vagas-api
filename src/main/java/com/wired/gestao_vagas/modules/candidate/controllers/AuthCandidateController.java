package com.wired.gestao_vagas.modules.candidate.controllers;

import javax.naming.AuthenticationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wired.gestao_vagas.exceptions.NotFoundException;
import com.wired.gestao_vagas.modules.candidate.dtos.AuthCandidateDTO;
import com.wired.gestao_vagas.modules.candidate.dtos.AuthCandidateResponseDTO;
import com.wired.gestao_vagas.modules.candidate.useCases.AuthCandidateUseCase;

@RestController
@RequestMapping("/candidates")
public class AuthCandidateController {

    @Autowired
    private AuthCandidateUseCase authCandidateUseCase;

    @PostMapping("/auth")
    public ResponseEntity<Object> auth(@RequestBody AuthCandidateDTO data) {
        try {
            AuthCandidateResponseDTO authCandidateResponse = authCandidateUseCase.execute(data);

            return ResponseEntity.ok(authCandidateResponse);
        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        } catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

}
