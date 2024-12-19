package com.wired.gestao_vagas.modules.candidate.useCases;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.wired.gestao_vagas.modules.candidate.repositories.CandidateRepository;
import com.wired.gestao_vagas.modules.candidate.entities.CandidateEntity;
import org.springframework.http.HttpStatus;

@Service
public class GetCandidatesUseCase {

    @Autowired
    private CandidateRepository candidatesRepository;

    @ResponseStatus(HttpStatus.OK)
    public List<CandidateEntity> execute() {
        return candidatesRepository.findAll();
    }
}
