package com.example.recipeshyperskill.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.httpBasic()
                .authenticationEntryPoint(new RestAuthenticationEntryPoint()) // Handle auth error
                .and()
                .csrf().disable().headers().frameOptions().disable() // for Postman, the H2 console
                .and()
                .authorizeHttpRequests() // manage access
                .requestMatchers(HttpMethod.POST, ("/api/register/**")).permitAll()
                .requestMatchers(HttpMethod.POST, ("/api/recipe/new/**")).hasRole("USER")
                .requestMatchers(HttpMethod.PUT, ("/api/recipe/**")).hasRole("USER")
                .requestMatchers(HttpMethod.GET, ("/api/recipe/search/**")).hasRole("USER")
                .requestMatchers(HttpMethod.GET, ("/api/recipe/**")).hasRole("USER")
                .requestMatchers(HttpMethod.DELETE, ("/api/recipe/**")).hasRole("USER")
                .anyRequest().permitAll()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        return http.build();
    }


}