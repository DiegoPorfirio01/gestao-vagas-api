package com.wired.gestao_vagas.providers.jwt;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

@Service
public class CompanyTokenStrategy implements InterfaceTokenStrategy {

    @Value("${security.token.secret.company}")
    private String secretKeyCompany;

    @Override
    public String generateToken(String subject) {
        Algorithm algorithm = Algorithm.HMAC256(secretKeyCompany);
        return JWT.create()
                .withIssuer("api-gestao-vagas")
                .withSubject(subject)
                .withExpiresAt(Instant.now().plus(24, ChronoUnit.HOURS))
                .sign(algorithm);
    }

    @Override
    public String validateToken(String token) {
        Algorithm algorithm = Algorithm.HMAC256(secretKeyCompany);
        JWTVerifier verifier = JWT.require(algorithm)
                .withIssuer("api-gestao-vagas")
                .build();
        DecodedJWT jwt = verifier.verify(token);
        return jwt.getSubject();
    }

    @Override
    public boolean supportsRole(String role) {
        return "company".equals(role);
    }

    @Override
    public Long getExpiration(String token) {
        return JWT.decode(token).getExpiresAt().toInstant().getEpochSecond();
    }
}
