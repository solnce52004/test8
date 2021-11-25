package com.example.test8.controller;


import com.example.test8.config.jwt.AuthenticationService;
import com.example.test8.entity.User;
import com.example.test8.service.UserDetailsSecurityService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Controller
@AllArgsConstructor
@Slf4j

public class LoginController {

    private final AuthenticationService authenticationService;
    private final PasswordEncoder passwordEncoder;

    private final UserDetailsSecurityService userService;
    private final static ResponseEntity<Object> UNAUTHORIZED =
            ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

    @GetMapping(path = {"/login"})
    public Mono<ServerResponse> login() {

        String view = authenticationService.isAuthenticated()
                ? "success"
                : "login";

        return ServerResponse
                .ok()
                .contentType(MediaType.TEXT_HTML)
                .render(view);
    }

    @PostMapping("/login")
    public Mono<ResponseEntity<?>> login(ServerWebExchange swe) {
        return swe
                .getFormData()
                .flatMap(credentials ->
                        userService
                                .findByUsername(credentials.getFirst("username"))
                                .cast(User.class)
                                .map(userDetails ->
                                        {
                                            if (passwordEncoder.matches(
                                                    credentials.getFirst("password"),
                                                    userDetails.getPassword()
                                            )) {
                                                return ResponseEntity.ok(
                                                        authenticationService.autoLogin(userDetails));
                                            } else {
                                                return UNAUTHORIZED;
                                            }
                                        }
                                )
                                .defaultIfEmpty(UNAUTHORIZED)
                );
    }
}
