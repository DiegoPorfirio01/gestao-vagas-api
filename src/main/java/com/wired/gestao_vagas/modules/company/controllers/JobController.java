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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.wired.gestao_vagas.exceptions.NotFoundException;
import com.wired.gestao_vagas.modules.company.dtos.CreateJobDTO;
import com.wired.gestao_vagas.modules.company.entities.JobEntity;
import com.wired.gestao_vagas.modules.company.useCases.CreateJobUseCase;
import com.wired.gestao_vagas.modules.company.useCases.GetJobsUseCase;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
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
    @Tag(name = "Jobs")
    @Operation(summary = "Create a job from current company")
    @SecurityRequirement(name = "Bearer Authentication")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Job created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request body"),
            @ApiResponse(responseCode = "404", description = "Company not found")
    })

    public ResponseEntity<Object> create(@Valid @RequestBody CreateJobDTO job, HttpServletRequest request) {
        UUID companyId = UUID.fromString(request.getAttribute("company_id").toString());

        JobEntity jobEntity = JobEntity.builder()
                .title(job.getTitle())
                .salary(job.getSalary())
                .companyId(companyId)
                .build();

        this.createJobUseCase.execute(jobEntity);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    // Public (List all jobs from all companies)
    @GetMapping("/jobs")
    @Tag(name = "Jobs")
    @Operation(summary = "List all jobs from all companies")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Jobs listed successfully")
    })
    public ResponseEntity<Object> list(
            @RequestParam(required = false) String title) {

        return ResponseEntity.ok(this.getJobsUseCase.execute(title));
    }
}
