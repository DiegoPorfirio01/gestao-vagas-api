package com.wired.gestao_vagas.modules.candidate.dtos;

import org.hibernate.validator.constraints.Length;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

@Schema(description = "DTO for creating a candidate")
public record CreateCandidateBodyDTO(
                @Schema(description = "Candidate name", example = "John Doe") @NotBlank(message = "Name is required") @Length(min = 3, max = 255, message = "Name must be between 3 and 255 characters") String name,

                @Schema(description = "Candidate username", example = "johndoe") @Pattern(regexp = "^[a-zA-Z0-9]+$", message = "Username must contain only letters and numbers and cannot contain spaces") @NotBlank(message = "Username is required") @Length(min = 3, max = 20, message = "Username must be between 3 and 20 characters") String username,

                @Schema(description = "Candidate email", example = "john.doe@example.com") @Email(message = "Invalid email") @NotBlank(message = "Email is required") String email,

                @Schema(description = "Candidate password", example = "12345678") @Length(min = 8, message = "Password must be at least 8 characters") @NotBlank(message = "Password is required") String password,

                @Schema(description = "Candidate description", example = "John Doe is a full stack developer with 5 years of experience") String description,

                @Schema(description = "Candidate curriculum", example = "https://www.linkedin.com/in/john-doe-1234567890") String curriculum) {
}
