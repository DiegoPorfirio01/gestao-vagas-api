package com.wired.gestao_vagas.providers.jwt;

import java.util.List;

import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;

@Service
public class JWTProvider {
    private final List<InterfaceTokenStrategy> tokenStrategies;

    public JWTProvider(List<InterfaceTokenStrategy> tokenStrategies) {
        this.tokenStrategies = tokenStrategies;
    }

    public String generateToken(String subject, String role) {
        return tokenStrategies.stream()
                .filter(strategy -> strategy.supportsRole(role))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("No token strategy found for role: " + role))
                .generateToken(subject);
    }

    public String validateToken(String token, String role) {
        return tokenStrategies.stream()
                .filter(strategy -> strategy.supportsRole(role))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("No token strategy found for role: " + role))
                .validateToken(token);
    }

    public Long getExpiration(String token) {
        try {
            return JWT.decode(token).getExpiresAt().toInstant().getEpochSecond();
        } catch (Exception e) {
            return null;
        }
    }
}
