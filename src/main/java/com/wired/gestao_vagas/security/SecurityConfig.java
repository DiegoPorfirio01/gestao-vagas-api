package com.wired.gestao_vagas.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    SecurityFilterCompany securityFilterCompany;

    @Autowired
    SecurityFilterCandidate securityFilterCandidate;

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> {
                    auth
                            .requestMatchers(HttpMethod.POST, "/companies/auth").permitAll()
                            .requestMatchers(HttpMethod.POST, "/candidates/auth").permitAll()
                            .requestMatchers(HttpMethod.POST, "/candidates").permitAll()
                            .requestMatchers(HttpMethod.GET, "/companies").permitAll()
                            .requestMatchers(HttpMethod.POST, "/companies").permitAll()
                            .requestMatchers(HttpMethod.GET, "/companies/jobs").permitAll()
                            .anyRequest().authenticated();
                }).addFilterBefore(securityFilterCompany, BasicAuthenticationFilter.class)
                .addFilterBefore(securityFilterCandidate, BasicAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    PasswordEncoder PasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
