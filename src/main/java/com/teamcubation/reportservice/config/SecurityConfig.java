package com.teamcubation.reportservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(crs -> crs.disable())
                .authorizeHttpRequests(authRequest -> authRequest
                        .requestMatchers("/api/v1/auth/**").permitAll()
                        .requestMatchers("/h2-console/**").permitAll() // Permitir acceso a la consola H2
                        .requestMatchers("/mock/public/**").permitAll()
                        .requestMatchers("/mock/user/**").hasRole("USER")
                        .requestMatchers("/mock/admin/**").hasRole("ADMIN")
                        .anyRequest().authenticated()
                )
                .headers(headers -> headers.frameOptions(frameOptionsConfig -> frameOptionsConfig.disable()))  // Deshabilitar protección de frame para H2
                .httpBasic(Customizer.withDefaults()) // Habilitar autenticación basada en HTTP, User y Password en application.properties. En futuras tareas se va a  configurar para que sea en base a los users de la DB
                .build();
    }

    // Encriptador de contraseñas
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}


