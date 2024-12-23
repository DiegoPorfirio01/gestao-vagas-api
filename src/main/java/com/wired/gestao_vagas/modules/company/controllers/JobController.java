package com.wired.gestao_vagas.modules.company.controllers;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wired.gestao_vagas.exceptions.NotFoundException;
import com.wired.gestao_vagas.modules.company.dtos.CreateJobDTO;
import com.wired.gestao_vagas.modules.company.entities.JobEntity;
import com.wired.gestao_vagas.modules.company.useCases.CreateJobUseCase;
import com.wired.gestao_vagas.modules.company.useCases.GetJobsUseCase;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@RestController
@RequestMapping()
public class JobController {
    @Autowired
    private CreateJobUseCase createJobUseCase;

    @Autowired
    private GetJobsUseCase getJobsUseCase;

    // Private && ROLE_COMPANY (Create a job from current company)
    @PreAuthorize("hasRole('ROLE_COMPANY')")
    @PostMapping("/companies/jobs")
    public ResponseEntity<Object> create(@Valid @RequestBody CreateJobDTO job, HttpServletRequest request) {
        UUID companyId = UUID.fromString(request.getAttribute("company_id").toString());

        JobEntity jobEntity = JobEntity.builder()
                .title(job.getTitle())
                .salary(job.getSalary())
                .companyId(companyId)
                .build();
        try {
            this.createJobUseCase.execute(jobEntity);
        } catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    // Public (List all jobs from all companies)
    @GetMapping("/companies/jobs")
    public ResponseEntity<Object> list() {
        return ResponseEntity.ok(this.getJobsUseCase.execute());
    }
}
