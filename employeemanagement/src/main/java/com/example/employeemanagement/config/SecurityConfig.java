package com.example.employeemanagement.config;

import com.example.employeemanagement.security.CustomUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
public class SecurityConfig {

    private final CustomUserDetailsService userDetailsService;

    public SecurityConfig(CustomUserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/api/employees/**").hasAnyAuthority("ROLE_HR", "ROLE_ADMIN")
                .requestMatchers("/api/departments/**").hasAuthority("ROLE_ADMIN")
                .requestMatchers("/api/roles/**").hasAuthority("ROLE_ADMIN")
                .requestMatchers("/api/audit-trails/**").hasAuthority("ROLE_ADMIN")
                .anyRequest().authenticated()
            )
            .httpBasic(withDefaults()) // Use default HTTP Basic behavior
            .csrf(csrf -> csrf.disable()); // Disable CSRF for APIs

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}