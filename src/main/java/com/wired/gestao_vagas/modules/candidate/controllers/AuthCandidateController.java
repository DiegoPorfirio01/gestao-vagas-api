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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@Tag(name = "Auth")
@RequestMapping("/candidates")
public class AuthCandidateController {

    @Autowired
    private AuthCandidateUseCase authCandidateUseCase;

    // PUBLIC
    @PostMapping("/auth")
    @Operation(summary = "Authenticate a candidate")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Candidate authenticated successfully", content = @Content(schema = @Schema(implementation = AuthCandidateResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid request body", content = @Content(schema = @Schema(hidden = true))),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(schema = @Schema(hidden = true)))
    })
    public ResponseEntity<AuthCandidateResponseDTO> auth(@RequestBody @Valid AuthCandidateDTO data) {
        AuthCandidateResponseDTO authCandidateResponse = authCandidateUseCase.execute(data);

        return ResponseEntity.ok().body(authCandidateResponse);
    }

}
