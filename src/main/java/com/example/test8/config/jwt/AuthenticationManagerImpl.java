package com.example.test8.config.jwt;

import com.example.test8.config.exception.JwtAuthException;
import com.example.test8.service.UserDetailsSecurityService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@AllArgsConstructor

public class AuthenticationManagerImpl implements ReactiveAuthenticationManager {

    private final UserDetailsSecurityService userDetailsService;
    private final JwtTokenUtil jwtTokenUtil;

    @Override
    public Mono<Authentication> authenticate(Authentication authentication) {
        String authToken = authentication.getCredentials().toString();
        String username;

        try {
            username = jwtTokenUtil.getUsernameFromToken(authToken);
        } catch (Exception e) {
            throw new JwtAuthException("Invalid getUsernameFromToken", e, HttpStatus.UNAUTHORIZED);
        }

        if (username != null && jwtTokenUtil.validateToken(authToken)) {

          return userDetailsService
                    .findByUsername(username)
                    .map(
                            u -> new UsernamePasswordAuthenticationToken(
                                    u.getUsername(),
                                    null,
                                    u.getAuthorities()
                            )
                    );
        } else {
            return Mono.empty();
        }
    }
}
