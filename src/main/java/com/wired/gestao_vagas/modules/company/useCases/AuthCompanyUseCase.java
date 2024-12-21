package com.wired.gestao_vagas.modules.company.useCases;

import javax.naming.AuthenticationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.wired.gestao_vagas.exceptions.NotFoundException;
import com.wired.gestao_vagas.modules.company.dtos.AuthCompanyDTO;
import com.wired.gestao_vagas.modules.company.dtos.AuthCompanyResponse;
import com.wired.gestao_vagas.modules.company.entities.CompanyEntity;
import com.wired.gestao_vagas.modules.company.repositories.CompanyRepository;
import com.wired.gestao_vagas.providers.jwt.JWTProvider;

@Service
public class AuthCompanyUseCase {

    @Autowired
    private JWTProvider jwtProvider;
    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public AuthCompanyResponse execute(AuthCompanyDTO data) throws AuthenticationException {
        CompanyEntity company = companyRepository.findByEmail(data.getEmail())
                .orElseThrow(() -> new NotFoundException("Company not found"));

        if (!passwordEncoder.matches(data.getPassword(), company.getPassword())) {
            throw new AuthenticationException("Password or email incorrect");
        }

        var jwt = jwtProvider.generateToken(company.getId().toString(), "company");

        Long expiresIn = jwtProvider.getExpiration(jwt);

        AuthCompanyResponse authCompanyResponse = AuthCompanyResponse.builder()
                .token(jwt)
                .expiresIn(expiresIn)
                .build();

        return authCompanyResponse;
    }
}
