package com.wired.gestao_vagas.modules.company.entities;

import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.validator.constraints.Length;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
@Entity
@Table(name = "companies")
public class CompanyEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @NotBlank(message = "Nome é obrigatório")
    @Length(min = 3, max = 255, message = "Nome deve ter entre 3 e 255 caracteres")
    private String name;

    @Pattern(regexp = "^[a-zA-Z0-9]+$", message = "Slug deve conter apenas letras e números e não pode conter espaços")
    @Column(unique = true)
    private String slug;

    @Email(message = "Email inválido")
    @NotBlank(message = "Email é obrigatório")
    @Column(unique = true)
    private String email;

    @Pattern(regexp = "^www\\.[a-zA-Z0-9-]+\\.(com|com\\.br)$", message = "Website deve estar no formato www.dominio.com ou www.dominio.com.br")
    @NotBlank(message = "Website é obrigatório")
    private String website;

    @Length(min = 8, message = "A senha deve ter pelo menos 8 caracteres")
    @NotBlank(message = "A senha é obrigatória")
    private String password;

    private String description;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;
}
