package com.wired.gestao_vagas.modules.company.dtos;

import java.math.BigDecimal;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CreateJobDTO {
    @NotBlank(message = "Title is required")
    private String title;
    @NotNull(message = "Salary is required")
    private BigDecimal salary;
}
