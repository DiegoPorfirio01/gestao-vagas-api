package com.wired.gestao_vagas.modules.candidate.useCases;

import javax.naming.AuthenticationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.wired.gestao_vagas.exceptions.NotFoundException;
import com.wired.gestao_vagas.modules.candidate.dtos.AuthCandidateDTO;
import com.wired.gestao_vagas.modules.candidate.dtos.AuthCandidateResponseDTO;
import com.wired.gestao_vagas.modules.candidate.entities.CandidateEntity;
import com.wired.gestao_vagas.modules.candidate.repositories.CandidateRepository;
import com.wired.gestao_vagas.providers.jwt.JWTProvider;

@Service
public class AuthCandidateUseCase {
    @Autowired
    private CandidateRepository candidateRepository;

    @Autowired
    private JWTProvider jwtProvider;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public AuthCandidateResponseDTO execute(AuthCandidateDTO data) throws AuthenticationException {
        CandidateEntity candidate = this.candidateRepository.findByEmail(data.email())
                .orElseThrow(() -> new NotFoundException("Candidate not found"));

        if (!passwordEncoder.matches(data.password(), candidate.getPassword())) {
            throw new AuthenticationException("Password or email incorrect");
        }

        var jwt = jwtProvider.generateToken(candidate.getId().toString(), "candidate");

        Long expiresIn = jwtProvider.getExpiration(jwt);

        AuthCandidateResponseDTO authCandidateResponse = AuthCandidateResponseDTO.builder()
                .token(jwt)
                .expiresIn(expiresIn)
                .build();

        return authCandidateResponse;
    }
}
