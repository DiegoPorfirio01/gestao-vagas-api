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

import com.wired.gestao_vagas.modules.candidate.dtos.CreateApplyJobBodyDTO;
import com.wired.gestao_vagas.modules.candidate.dtos.CreateCandidateBodyDTO;
import com.wired.gestao_vagas.modules.candidate.dtos.GetCandidateResponseDTO;
import com.wired.gestao_vagas.modules.candidate.entities.CandidateEntity;
import com.wired.gestao_vagas.modules.candidate.useCases.CreateApplyJobCandidateUseCase;
import com.wired.gestao_vagas.modules.candidate.useCases.CreateCandidateUseCase;
import com.wired.gestao_vagas.modules.candidate.useCases.GetPerfilCandidateUseCase;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;

@RestController
@Tag(name = "Candidates")
@RequestMapping("/candidates")
public class CandidateController {

        @Autowired
        private CreateCandidateUseCase createCandidateUseCase;

        @Autowired
        private GetPerfilCandidateUseCase getPerfilCandidateUseCase;

        @Autowired
        private CreateApplyJobCandidateUseCase applyJobCandidateUseCase;

        // Private && ROLE_CANDIDATE (Get current candidate profile)
        @PreAuthorize("hasRole('ROLE_CANDIDATE')")
        @GetMapping("/me")
        @Operation(summary = "Get current candidate profile")
        @SecurityRequirement(name = "Bearer Authentication")
        @ApiResponses({
                        @ApiResponse(responseCode = "200", description = "Candidate profile retrieved successfully", content = @Content(schema = @Schema(implementation = GetCandidateResponseDTO.class))),
                        @ApiResponse(responseCode = "404", description = "Candidate not found", content = @Content(schema = @Schema(hidden = true))),
                        @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(schema = @Schema(hidden = true))),
                        @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content(schema = @Schema(hidden = true)))
        })
        public ResponseEntity<GetCandidateResponseDTO> getCurrentPerfilCandidate(HttpServletRequest request) {

                UUID candidateId = UUID.fromString(request.getAttribute("candidate_id").toString());

                CandidateEntity candidate = this.getPerfilCandidateUseCase.execute(candidateId);

                GetCandidateResponseDTO candidateResponse = GetCandidateResponseDTO.fromEntity(candidate);

                return ResponseEntity.ok(candidateResponse);
        }

        // Public (Create a user candidate)
        @PostMapping
        @Operation(summary = "Create a user candidate")
        @ApiResponses({
                        @ApiResponse(responseCode = "201", description = "Candidate created successfully"),
                        @ApiResponse(responseCode = "400", description = "Invalid request body"),
                        @ApiResponse(responseCode = "409", description = "Conflict with another candidate")
        })
        public ResponseEntity<Void> createCandidate(@RequestBody @Valid CreateCandidateBodyDTO candidate) {

                CandidateEntity candidateEntity = CandidateEntity.builder()
                                .name(candidate.name())
                                .username(candidate.username())
                                .email(candidate.email())
                                .password(candidate.password())
                                .description(candidate.description())
                                .curriculum(candidate.curriculum())
                                .build();

                this.createCandidateUseCase.execute(candidateEntity);

                return ResponseEntity.status(HttpStatus.CREATED).build();
        }

        @PostMapping("/apply_job")
        @PreAuthorize("hasRole('ROLE_CANDIDATE')")
        @Operation(summary = "Apply for a job")
        @ApiResponses({
                        @ApiResponse(responseCode = "201", description = "Job applied successfully"),
                        @ApiResponse(responseCode = "400", description = "Invalid request body"),
                        @ApiResponse(responseCode = "404", description = "Job not found"),
                        @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(schema = @Schema(hidden = true)))
        })
        @SecurityRequirement(name = "Bearer Authentication")
        public ResponseEntity<Void> applyJob(@RequestBody @Valid CreateApplyJobBodyDTO createApplyJobBodyDTO,
                        HttpServletRequest request) {
                UUID candidateId = UUID.fromString(request.getAttribute("candidate_id").toString());

                this.applyJobCandidateUseCase.execute(createApplyJobBodyDTO.jobId(), candidateId);

                return ResponseEntity.status(HttpStatus.CREATED).build();
        }
}
