package com.wired.gestao_vagas.modules.candidate.useCases;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;
import java.util.UUID;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.wired.gestao_vagas.exceptions.ConflictException;
import com.wired.gestao_vagas.exceptions.NotFoundException;
import com.wired.gestao_vagas.modules.candidate.entities.ApplyJobEntity;
import com.wired.gestao_vagas.modules.candidate.entities.CandidateEntity;
import com.wired.gestao_vagas.modules.candidate.repositories.CandidateRepository;
import com.wired.gestao_vagas.modules.candidate.repositories.ApplyJobRepository;
import com.wired.gestao_vagas.modules.company.entities.JobEntity;
import com.wired.gestao_vagas.modules.company.repositories.JobRepository;

@ExtendWith(MockitoExtension.class)
public class CreateApplyJobCandidateUseCaseTest {

    @InjectMocks
    private CreateApplyJobCandidateUseCase applyJobCandidateUseCase;

    @Mock
    private CandidateRepository candidateRepository;

    @Mock
    private JobRepository jobRepository;

    @Mock
    private ApplyJobRepository applyJobRepository;

    @Test
    @DisplayName("Should NOT apply job with candidate not found")
    public void shouldNotApplyJobWithCandidateNotFound() {
        // Arrange
        UUID candidateId = UUID.randomUUID();
        UUID jobId = UUID.randomUUID();

        when(candidateRepository.findById(candidateId))
                .thenReturn(Optional.empty());

        // Act & Assert
        Assertions.assertThatThrownBy(() -> {
            applyJobCandidateUseCase.execute(jobId, candidateId);
        }).isInstanceOf(NotFoundException.class)
                .hasMessage("Candidate not found");
    }

    @Test
    @DisplayName("Should NOT apply job with job not found")
    public void shouldNotApplyJobWithJobNotFound() {
        // Arrange
        UUID candidateId = UUID.randomUUID();
        UUID jobId = UUID.randomUUID();

        when(candidateRepository.findById(candidateId))
                .thenReturn(Optional.of(CandidateEntity.builder().id(candidateId).build()));

        when(jobRepository.findById(jobId))
                .thenReturn(Optional.empty());

        // Act & Assert
        Assertions.assertThatThrownBy(() -> {
            applyJobCandidateUseCase.execute(jobId, candidateId);
        }).isInstanceOf(NotFoundException.class)
                .hasMessage("Job not found");
    }

    @Test
    @DisplayName("Should apply job with success")
    public void shouldApplyJobWithSuccess() {
        // Arrange
        UUID candidateId = UUID.randomUUID();
        UUID jobId = UUID.randomUUID();

        CandidateEntity candidate = CandidateEntity.builder().id(candidateId).build();
        JobEntity job = JobEntity.builder().id(jobId).build();

        when(candidateRepository.findById(candidateId)).thenReturn(Optional.of(candidate));
        when(jobRepository.findById(jobId)).thenReturn(Optional.of(job));
        when(applyJobRepository.findByCandidateIdAndJobId(candidateId, jobId))
                .thenReturn(Optional.empty());

        // Act
        applyJobCandidateUseCase.execute(jobId, candidateId);

        // Assert
        ArgumentCaptor<ApplyJobEntity> applyJobCaptor = ArgumentCaptor.forClass(ApplyJobEntity.class);
        verify(applyJobRepository).save(applyJobCaptor.capture());

        ApplyJobEntity savedApplyJob = applyJobCaptor.getValue();
        Assertions.assertThat(savedApplyJob.getCandidateId()).isEqualTo(candidateId);
        Assertions.assertThat(savedApplyJob.getJobId()).isEqualTo(jobId);
        Assertions.assertThat(savedApplyJob.getStatus()).isEqualTo(ApplyJobEntity.CandidateJobStatus.APPLIED);
    }

    @Test
    @DisplayName("Should NOT apply when candidate already applied to the job")
    public void shouldNotApplyWhenCandidateAlreadyApplied() {
        // Arrange
        UUID candidateId = UUID.randomUUID();
        UUID jobId = UUID.randomUUID();

        CandidateEntity candidate = CandidateEntity.builder().id(candidateId).build();
        JobEntity job = JobEntity.builder().id(jobId).build();

        when(candidateRepository.findById(candidateId)).thenReturn(Optional.of(candidate));
        when(jobRepository.findById(jobId)).thenReturn(Optional.of(job));
        when(applyJobRepository.findByCandidateIdAndJobId(candidateId, jobId))
                .thenReturn(Optional.of(ApplyJobEntity.builder()
                        .candidateId(candidateId)
                        .jobId(jobId)
                        .status(ApplyJobEntity.CandidateJobStatus.APPLIED)
                        .build()));

        // Act & Assert
        Assertions.assertThatThrownBy(() -> {
            applyJobCandidateUseCase.execute(jobId, candidateId);
        }).isInstanceOf(ConflictException.class)
                .hasMessage("Candidate already applied to this job");
    }
}
