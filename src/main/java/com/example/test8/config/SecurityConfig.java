package com.example.test8.config;

import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;

@EnableWebFluxSecurity
@EnableReactiveMethodSecurity
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder(12);
    }

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity serverHttpSecurity){
        return serverHttpSecurity
                .csrf().disable()
                .formLogin()
                .disable()
//                .loginPage("/login")
//                .and()
                .httpBasic().disable()
                .authorizeExchange()
                .pathMatchers("/", "/index", "/login", "/integration/**", "/favicon.ico").permitAll()
                .pathMatchers(HttpMethod.POST, "/login").permitAll()
                .pathMatchers( "/re/message", "/integration/users").hasRole("ADMIN")
                .anyExchange().authenticated()
                .and()
                .build();
    }
}
