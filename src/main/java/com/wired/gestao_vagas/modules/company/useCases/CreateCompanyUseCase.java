package com.wired.gestao_vagas.modules.company.useCases;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.wired.gestao_vagas.exceptions.AlreadyExistsException;
import com.wired.gestao_vagas.modules.company.entities.CompanyEntity;
import com.wired.gestao_vagas.modules.company.repositories.CompanyRepository;
import com.wired.gestao_vagas.utils.StringUtils;
import org.springframework.http.HttpStatus;

@Service
public class CreateCompanyUseCase {
    @Autowired
    private CompanyRepository companyRepository;

    @ResponseStatus(HttpStatus.CREATED)
    public void execute(CompanyEntity company) {
        this.generateSlug(company);

        this.companyRepository.findBySlug(company.getSlug())
                .ifPresent(user -> {
                    throw new AlreadyExistsException("Company with this name already exists");
                });

        this.companyRepository.findByEmail(company.getEmail())
                .ifPresent(user -> {
                    throw new AlreadyExistsException("Email já está em uso");
                });

        this.companyRepository.save(company);
    }

    private void generateSlug(CompanyEntity company) {
        String slug = StringUtils.generateSlug(company.getName());
        company.setSlug(slug);
    }
}
