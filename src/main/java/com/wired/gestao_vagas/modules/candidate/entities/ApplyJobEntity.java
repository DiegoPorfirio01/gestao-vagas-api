package com.wired.gestao_vagas.modules.candidate.entities;

import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import com.wired.gestao_vagas.modules.company.entities.JobEntity;

@Entity
@Table(name = "apply_jobs", uniqueConstraints = {
        @UniqueConstraint(columnNames = { "candidate_id", "job_id" })
})
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ApplyJobEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "candidate_id", insertable = false, updatable = false)
    private CandidateEntity candidate;

    @ManyToOne
    @JoinColumn(name = "job_id", insertable = false, updatable = false)
    private JobEntity job;

    @Column(name = "candidate_id")
    private UUID candidateId;

    @Column(name = "job_id")
    private UUID jobId;

    @Enumerated(EnumType.STRING)
    private CandidateJobStatus status;

    @CreationTimestamp
    private LocalDateTime createdAt;

    public enum CandidateJobStatus {
        APPLIED,
        REVIEWING,
        INTERVIEWED,
        APPROVED,
        REJECTED
    }
}
