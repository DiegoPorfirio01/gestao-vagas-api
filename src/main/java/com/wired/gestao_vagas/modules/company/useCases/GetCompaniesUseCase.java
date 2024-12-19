package com.wired.gestao_vagas.modules.company.useCases;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.wired.gestao_vagas.modules.company.entities.CompanyEntity;
import com.wired.gestao_vagas.modules.company.repositories.CompanyRepository;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;

@Service
public class GetCompaniesUseCase {
    @Autowired
    private CompanyRepository companyRepository;

    @ResponseStatus(HttpStatus.OK)
    public List<CompanyEntity> execute() {
        return companyRepository.findAll();
    }
}
