package com.wired.gestao_vagas.modules.candidate.useCases;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wired.gestao_vagas.exceptions.NotFoundException;
import com.wired.gestao_vagas.modules.candidate.entities.ApplyJobEntity;
import com.wired.gestao_vagas.modules.candidate.entities.ApplyJobEntity.CandidateJobStatus;
import com.wired.gestao_vagas.modules.candidate.repositories.CandidateRepository;
import com.wired.gestao_vagas.modules.company.repositories.JobRepository;
import com.wired.gestao_vagas.modules.candidate.repositories.ApplyJobRepository;

@Service
public class ApplyJobCandidateUseCase {
    @Autowired
    private CandidateRepository candidateRepository;

    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private ApplyJobRepository applyJobRepository;

    public void execute(UUID jobId, UUID candidateId) {
        candidateRepository.findById(candidateId)
                .orElseThrow(() -> new NotFoundException("Candidate not found"));
        jobRepository.findById(jobId).orElseThrow(() -> new NotFoundException("Job not found"));

        ApplyJobEntity applyJob = ApplyJobEntity.builder()
                .candidateId(candidateId)
                .jobId(jobId)
                .status(CandidateJobStatus.APPLIED)
                .build();

        applyJobRepository.save(applyJob);
    }
}
