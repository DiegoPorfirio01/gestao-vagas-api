package com.wired.gestao_vagas.modules.company.useCases;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.wired.gestao_vagas.exceptions.NotFoundException;
import com.wired.gestao_vagas.modules.company.entities.JobEntity;
import com.wired.gestao_vagas.modules.company.repositories.CompanyRepository;
import com.wired.gestao_vagas.modules.company.repositories.JobRepository;

@Service
public class CreateJobUseCase {
    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private CompanyRepository companyRepository;

    @ResponseStatus(HttpStatus.CREATED)
    public void execute(JobEntity job) {
        System.out.println("job: " + job);
        this.companyRepository.findById(job.getCompanyId())
                .orElseThrow(() -> new NotFoundException("Company not found"));

        this.jobRepository.save(job);
    }
}
