package com.wired.gestao_vagas.modules.candidate.useCases;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.wired.gestao_vagas.exceptions.AlreadyExistsException;
import com.wired.gestao_vagas.modules.candidate.entities.CandidateEntity;
import com.wired.gestao_vagas.modules.candidate.repositories.CandidateRepository;

@Service
public class CreateCandidateUseCase {
    @Autowired
    private CandidateRepository candidatesRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @ResponseStatus(HttpStatus.CREATED)
    public void execute(CandidateEntity candidate) {
        // Verifica username
        this.candidatesRepository.findByUsername(candidate.getUsername())
                .ifPresent(user -> {
                    throw new AlreadyExistsException("username", "Username already exists");
                });

        // Verifica email
        this.candidatesRepository.findByEmail(candidate.getEmail())
                .ifPresent(user -> {
                    throw new AlreadyExistsException("email", "Email already exists");
                });

        candidate.setPassword(passwordEncoder.encode(candidate.getPassword()));

        this.candidatesRepository.save(candidate);
    }
}
