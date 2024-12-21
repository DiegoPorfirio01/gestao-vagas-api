package com.wired.gestao_vagas.providers.jwt;

public interface InterfaceTokenStrategy {
    String generateToken(String subject);

    String validateToken(String token);

    Long getExpiration(String token);

    boolean supportsRole(String role);
}
