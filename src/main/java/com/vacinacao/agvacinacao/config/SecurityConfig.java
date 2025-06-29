package com.vacinacao.agvacinacao.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.config.Customizer;

@Configuration
public class SecurityConfig {

    @Autowired
    private JwtFilter jwtFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .cors(Customizer.withDefaults()) // <---- AQUI
            .csrf(AbstractHttpConfigurer::disable)
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/api/auth/login").permitAll()
                .requestMatchers(HttpMethod.POST, "/api/usuarios").permitAll()
                .requestMatchers(HttpMethod.DELETE, "/api/usuarios/**").hasAuthority("ADMIN")
                .requestMatchers(HttpMethod.POST, "/api/pacientes").permitAll()
                .requestMatchers(HttpMethod.PUT, "/api/usuarios/recuperar-senha").permitAll()
                .requestMatchers(HttpMethod.GET, "/doencas").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/agendamentos/meus").hasAnyAuthority("PACIENTE", "ADMIN")
                .requestMatchers("/api/vacinas/**").hasAuthority("ADMIN")
                .requestMatchers("/api/agendamentos/**").hasAnyAuthority("PACIENTE","ADMIN")
                .requestMatchers("/api/pacientes/**").hasAnyAuthority("ADMIN", "PACIENTE")
                .requestMatchers("/api/doencas/**").hasAuthority("ADMIN")
                .anyRequest().authenticated())
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
