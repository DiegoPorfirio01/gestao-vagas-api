package com.wired.gestao_vagas.modules.candidate.useCases;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wired.gestao_vagas.exceptions.ConflictException;
import com.wired.gestao_vagas.exceptions.NotFoundException;
import com.wired.gestao_vagas.modules.candidate.entities.ApplyJobEntity;
import com.wired.gestao_vagas.modules.candidate.repositories.CandidateRepository;
import com.wired.gestao_vagas.modules.company.repositories.JobRepository;
import com.wired.gestao_vagas.modules.candidate.repositories.ApplyJobRepository;

@Service
public class CreateApplyJobCandidateUseCase {
    @Autowired
    private CandidateRepository candidateRepository;

    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private ApplyJobRepository applyJobRepository;

    public void execute(UUID jobId, UUID candidateId) {
        // Validate candidate
        this.candidateRepository.findById(candidateId)
                .orElseThrow(() -> new NotFoundException("Candidate not found"));

        // Validate job
        this.jobRepository.findById(jobId)
                .orElseThrow(() -> new NotFoundException("Job not found"));

        // Check if already applied
        this.applyJobRepository.findByCandidateIdAndJobId(candidateId, jobId)
                .ifPresent(applyJob -> {
                    throw new ConflictException("Candidate already applied to this job");
                });

        // Create and save the application
        ApplyJobEntity applyJob = ApplyJobEntity.builder()
                .candidateId(candidateId)
                .jobId(jobId)
                .status(ApplyJobEntity.CandidateJobStatus.APPLIED)
                .build();

        this.applyJobRepository.save(applyJob);
    }
}
