package com.wired.gestao_vagas.providers.jwt;

import com.auth0.jwt.interfaces.DecodedJWT;

public interface InterfaceTokenStrategy {
    String generateToken(String subject);

    DecodedJWT validateToken(String token);

    Long getExpiration(String token);

    boolean supportsRole(String role);
}
