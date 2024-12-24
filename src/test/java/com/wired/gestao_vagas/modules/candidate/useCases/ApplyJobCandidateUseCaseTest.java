package com.wired.gestao_vagas.modules.candidate.useCases;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;
import java.util.UUID;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.wired.gestao_vagas.exceptions.NotFoundException;
import com.wired.gestao_vagas.modules.candidate.entities.ApplyJobEntity;
import com.wired.gestao_vagas.modules.candidate.entities.CandidateEntity;
import com.wired.gestao_vagas.modules.candidate.repositories.CandidateRepository;
import com.wired.gestao_vagas.modules.candidate.repositories.ApplyJobRepository;
import com.wired.gestao_vagas.modules.company.entities.JobEntity;
import com.wired.gestao_vagas.modules.company.repositories.JobRepository;

@ExtendWith(MockitoExtension.class)
public class ApplyJobCandidateUseCaseTest {

    @InjectMocks
    private ApplyJobCandidateUseCase applyJobCandidateUseCase;

    @Mock
    private CandidateRepository candidateRepository;

    @Mock
    private JobRepository jobRepository;

    @Mock
    private ApplyJobRepository applyJobRepository;

    @Test
    @DisplayName("Should NOT apply job with candidate not found")
    public void shouldNotApplyJobWithCandidateNotFound() {
        try {
            applyJobCandidateUseCase.execute(null, null);
        } catch (Exception e) {
            Assertions.assertThat(e).isInstanceOf(NotFoundException.class);
        }
    }

    @Test
    @DisplayName("Should NOT apply job with job not found")
    public void shouldNotApplyJobWithJobNotFound() {
        UUID candidateId = UUID.randomUUID();

        when(candidateRepository.findById(candidateId)).thenReturn(Optional.empty());

        try {
            applyJobCandidateUseCase.execute(null, candidateId);
        } catch (Exception e) {
            Assertions.assertThat(e).isInstanceOf(NotFoundException.class);
        }
    }

    @Test
    @DisplayName("Should apply job with success")
    public void shouldApplyJobWithSuccess() {
        UUID candidateId = UUID.randomUUID();
        UUID jobId = UUID.randomUUID();

        CandidateEntity candidate = CandidateEntity.builder().id(candidateId).build();
        JobEntity job = JobEntity.builder().id(jobId).build();

        when(candidateRepository.findById(candidateId)).thenReturn(Optional.of(candidate));
        when(jobRepository.findById(jobId)).thenReturn(Optional.of(job));

        ApplyJobEntity applyJob = ApplyJobEntity.builder()
                .candidateId(candidateId)
                .jobId(jobId)
                .status(ApplyJobEntity.CandidateJobStatus.APPLIED)
                .build();

        when(applyJobRepository.save(applyJob)).thenReturn(applyJob);

        applyJobCandidateUseCase.execute(jobId, candidateId);

        verify(applyJobRepository).save(applyJob);
    }
}
