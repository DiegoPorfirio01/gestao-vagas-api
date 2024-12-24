package com.wired.gestao_vagas.modules.candidate.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wired.gestao_vagas.modules.candidate.dtos.CreateApplyJobBodyDTO;
import com.wired.gestao_vagas.modules.candidate.useCases.ApplyJobCandidateUseCase;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/apply-job")
public class ApplyJobController {

    @Autowired
    private ApplyJobCandidateUseCase applyJobCandidateUseCase;

    @PostMapping
    public ResponseEntity<Object> applyJob(@RequestBody @Valid CreateApplyJobBodyDTO createApplyJobBodyDTO) {
        applyJobCandidateUseCase.execute(createApplyJobBodyDTO.jobId(), createApplyJobBodyDTO.candidateId());

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
