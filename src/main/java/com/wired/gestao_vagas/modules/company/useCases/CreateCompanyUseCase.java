package com.wired.gestao_vagas.modules.company.useCases;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.wired.gestao_vagas.exceptions.AlreadyExistsException;
import com.wired.gestao_vagas.modules.company.entities.CompanyEntity;
import com.wired.gestao_vagas.modules.company.repositories.CompanyRepository;
import com.wired.gestao_vagas.utils.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;

@Service
public class CreateCompanyUseCase {
    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @ResponseStatus(HttpStatus.CREATED)
    public void execute(CompanyEntity company) {
        this.generateSlug(company);

        this.companyRepository.findBySlug(company.getSlug())
                .ifPresent(user -> {
                    throw new AlreadyExistsException("name", "Company with this name already exists");
                });

        this.companyRepository.findByEmail(company.getEmail())
                .ifPresent(user -> {
                    throw new AlreadyExistsException("email", "Email já está em uso");
                });

        var encodedPassword = this.passwordEncoder.encode(company.getPassword());

        System.out.println("DEBUG: " + encodedPassword);
        company.setPassword(encodedPassword);

        System.out.println("DEBUG: " + company.getPassword());

        this.companyRepository.save(company);
    }

    private void generateSlug(CompanyEntity company) {
        String slug = StringUtils.generateSlug(company.getName());
        company.setSlug(slug);
    }
}
