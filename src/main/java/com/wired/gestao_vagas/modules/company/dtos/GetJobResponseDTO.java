package com.wired.gestao_vagas.modules.company.dtos;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import com.wired.gestao_vagas.modules.company.entities.JobEntity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import io.swagger.v3.oas.annotations.media.Schema;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Data of the job")
public class GetJobResponseDTO {
    @Schema(description = "Job ID", example = "1")
    private UUID id;

    @Schema(description = "Title of the job", example = "Software Engineer")
    private String title;

    @Schema(description = "Salary of the job", example = "10000.00")
    private BigDecimal salary;

    @Schema(description = "Description of the job", example = "A Wired is a technology company that develops solutions for the market")
    private String description;

    @Schema(description = "Company ID", example = "3fa85f64-5717-4562-b3fc-2c963f66afa6")
    private UUID companyId;

    @Schema(description = "If the job is active", example = "true")
    private boolean isActive;

    @Schema(description = "Created at of the job", example = "2024-01-01")
    private LocalDateTime createdAt;

    public static GetJobResponseDTO fromEntity(JobEntity job) {
        return GetJobResponseDTO.builder()
                .id(job.getId())
                .title(job.getTitle())
                .salary(job.getSalary())
                .companyId(job.getCompanyId())
                .isActive(job.getActive())
                .createdAt(job.getCreatedAt())
                .build();
    }
}
