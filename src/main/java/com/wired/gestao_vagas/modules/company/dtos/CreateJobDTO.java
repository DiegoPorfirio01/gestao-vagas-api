package com.wired.gestao_vagas.modules.company.dtos;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CreateJobDTO {
    private String title;
    private BigDecimal salary;
}
