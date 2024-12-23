package com.wired.gestao_vagas.modules.candidate.dtos;

import org.hibernate.validator.constraints.Length;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

@Schema(description = "DTO para criação de candidato")
public record CreateCandidateBodyDTO(
        @Schema(description = "Nome do candidato", example = "John Doe") @NotBlank(message = "Nome é obrigatório") @Length(min = 3, max = 255, message = "Nome deve ter entre 3 e 255 caracteres") String name,

        @Schema(description = "Username do candidato", example = "john_doe") @Pattern(regexp = "^[a-zA-Z0-9]+$", message = "Username deve conter apenas letras e números e não pode conter espaços") @NotBlank(message = "Username é obrigatório") @Length(min = 3, max = 20, message = "Username deve ter entre 3 e 20 caracteres") String username,

        @Schema(description = "Email do candidato", example = "john.doe@example.com") @Email(message = "Email inválido") @NotBlank(message = "Email é obrigatório") String email,

        @Schema(description = "Senha do candidato", example = "12345678") @Length(min = 8, message = "Senha deve ter pelo menos 8 caracteres") @NotBlank(message = "Senha é obrigatório") String password,

        @Schema(description = "Descrição do candidato", example = "John Doe é um desenvolvedor full stack com 5 anos de experiência") String description,

        @Schema(description = "Currículo do candidato", example = "https://www.linkedin.com/in/john-doe-1234567890") String curriculum) {
}
