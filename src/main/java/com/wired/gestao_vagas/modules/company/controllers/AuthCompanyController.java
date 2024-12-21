package com.wired.gestao_vagas.modules.company.controllers;

import javax.naming.AuthenticationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wired.gestao_vagas.exceptions.NotFoundException;
import com.wired.gestao_vagas.modules.company.dtos.AuthCompanyDTO;
import com.wired.gestao_vagas.modules.company.dtos.AuthCompanyResponse;
import com.wired.gestao_vagas.modules.company.useCases.AuthCompanyUseCase;

@RestController
@RequestMapping("/companies")
public class AuthCompanyController {

    @Autowired
    private AuthCompanyUseCase authCompanyUseCase;

    @PostMapping("/auth")
    public ResponseEntity<Object> auth(@RequestBody AuthCompanyDTO data) {
        try {
            AuthCompanyResponse authCompanyResponse = authCompanyUseCase.execute(data);

            return ResponseEntity.ok(authCompanyResponse);
        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        } catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
