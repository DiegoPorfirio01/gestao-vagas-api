package com.wired.gestao_vagas.modules.candidate.useCases;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.wired.gestao_vagas.modules.candidate.dtos.AuthCandidateDTO;
import com.wired.gestao_vagas.modules.candidate.dtos.AuthCandidateResponseDTO;
import com.wired.gestao_vagas.modules.candidate.entities.CandidateEntity;
import com.wired.gestao_vagas.modules.candidate.repositories.CandidateRepository;
import com.wired.gestao_vagas.providers.jwt.JWTProvider;
import com.wired.gestao_vagas.exceptions.AuthenticationException;

@Service
public class AuthCandidateUseCase {
    @Autowired
    private CandidateRepository candidateRepository;

    @Autowired
    private JWTProvider jwtProvider;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public AuthCandidateResponseDTO execute(AuthCandidateDTO data) {
        CandidateEntity candidate = this.candidateRepository.findByEmail(data.email())
                .orElseThrow(() -> new AuthenticationException("Email or password incorrect"));

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
