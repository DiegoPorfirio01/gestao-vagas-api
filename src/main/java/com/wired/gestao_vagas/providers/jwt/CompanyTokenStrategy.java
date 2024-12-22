package com.wired.gestao_vagas.providers.jwt;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.exceptions.JWTVerificationException;

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
                .withClaim("roles", Arrays.asList("company"))
                .withExpiresAt(Instant.now().plus(24, ChronoUnit.HOURS))
                .sign(algorithm);
    }

    @Override
    public String validateToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secretKeyCompany);
            JWTVerifier verifier = JWT.require(algorithm)
                    .withIssuer("api-gestao-vagas")
                    .withClaimPresence("roles")
                    .build();

            DecodedJWT jwt = verifier.verify(token);

            if (jwt.getExpiresAt().before(new Date())) {
                throw new JWTVerificationException("Token expirado");
            }

            List<String> roles = jwt.getClaim("roles").asList(String.class);
            if (!roles.contains("company")) {
                throw new JWTVerificationException("Token não possui permissão de empresa");
            }

            return jwt.getSubject();
        } catch (JWTVerificationException exception) {
            throw new JWTVerificationException("Token inválido: " + exception.getMessage());
        }
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
