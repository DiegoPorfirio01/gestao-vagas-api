package com.wired.gestao_vagas.modules.company.dtos;

import org.hibernate.validator.constraints.Length;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Body of the company creation")
public class CreateCompanyBodyDTO {
    @NotBlank(message = "Nome é obrigatório")
    @Length(min = 3, max = 255, message = "Nome deve ter entre 3 e 255 caracteres")
    @Schema(description = "Nome da empresa", example = "Wired")
    private String name;

    @Email(message = "Email inválido")
    @NotBlank(message = "Email é obrigatório")
    @Column(unique = true)
    @Schema(description = "Email da empresa", example = "wired@gmail.com")
    private String email;

    @Pattern(regexp = "^www\\.[a-zA-Z0-9-]+\\.(com|com\\.br)$", message = "Website deve estar no formato www.dominio.com ou www.dominio.com.br")
    @NotBlank(message = "Website é obrigatório")
    @Schema(description = "Website da empresa", example = "www.wired.com")
    private String website;

    @Length(min = 8, message = "A senha deve ter pelo menos 8 caracteres")
    @NotBlank(message = "A senha é obrigatória")
    @Schema(description = "Senha da empresa", example = "12345678")
    private String password;

    @Schema(description = "Descrição da empresa", example = "A Wired é uma empresa de tecnologia que desenvolve soluções para o mercado")
    private String description;
}
