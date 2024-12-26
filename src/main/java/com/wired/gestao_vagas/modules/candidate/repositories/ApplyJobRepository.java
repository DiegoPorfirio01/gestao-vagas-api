package com.wired.gestao_vagas.modules.candidate.repositories;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wired.gestao_vagas.modules.candidate.entities.ApplyJobEntity;

public interface ApplyJobRepository extends JpaRepository<ApplyJobEntity, UUID> {
    Optional<ApplyJobEntity> findByCandidateIdAndJobId(UUID candidateId, UUID jobId);
}
