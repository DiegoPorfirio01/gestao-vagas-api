package com.wired.gestao_vagas.modules.company.dtos;

import java.math.BigDecimal;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Schema(description = "Body of the job creation")
public class CreateJobBodyDTO {
    @NotBlank(message = "Title is required")
    @Schema(description = "Title of the job", example = "Software Engineer")
    private String title;
    @NotNull(message = "Salary is required")
    @Schema(description = "Salary of the job", example = "10000.00")
    private BigDecimal salary;
}
