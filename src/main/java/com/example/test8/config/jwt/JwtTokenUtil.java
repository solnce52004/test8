package com.example.test8.config.jwt;

import com.example.test8.config.exception.JwtAuthException;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import javax.annotation.PostConstruct;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Date;

@Component
@PropertySource(value = {"classpath:application.yaml"})

public class JwtTokenUtil {

    @Value("${jwt.header}")
    public String authHeader;

    @Value("${jwt.secret}")
    public String secretKey;

    @Value("${jwt.expiration}")
    public long expirationInMs;

    @PostConstruct
    protected void init() {
        secretKey = Base64
                .getEncoder()
                .encodeToString(
                        secretKey.getBytes(StandardCharsets.UTF_8)
                );
    }

    public String createToken(UserDetails user) {
        final Claims claims = Jwts
                .claims()
                .setSubject(user.getUsername());
        claims.put("password", user.getPassword());
        claims.put("role", user.getAuthorities());

        final Date dateNow = new Date();
        final Date dateExpiration = new Date(dateNow.getTime() + expirationInMs);

        return Jwts
                .builder()
                .setClaims(claims)
                .setSubject(user.getUsername())
                .setIssuedAt(dateNow)
                .setExpiration(dateExpiration)
                .signWith(
                        Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretKey)),
                        SignatureAlgorithm.HS256
                )
                .compact();
    }

    public boolean validateToken(String token) throws JwtAuthException {
        final Claims claimsJws;
        try {
            claimsJws = getClaimsFromToken(token);

            // НЕ протух?
            return claimsJws.getExpiration().after(new Date());

        } catch (SignatureException e) {
            throw new JwtAuthException("Invalid JWT signature", e, HttpStatus.UNAUTHORIZED);
        } catch (MalformedJwtException e) {
            throw new JwtAuthException("Invalid JWT token", e, HttpStatus.UNAUTHORIZED);
        } catch (ExpiredJwtException e) {
            throw new JwtAuthException("JWT token is expired", e, HttpStatus.UNAUTHORIZED);
        } catch (UnsupportedJwtException e) {
            throw new JwtAuthException("JWT token is unsupported", e, HttpStatus.UNAUTHORIZED);
        } catch (IllegalArgumentException e) {
            throw new JwtAuthException("JWT claims string is empty", e, HttpStatus.UNAUTHORIZED);
        }
    }

    public String getUsernameFromToken(String token) {
        return getClaimsFromToken(token)
                .getSubject();
    }

    public Claims getClaimsFromToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public String resolveToken (ServerWebExchange exchange) {
        String authHeader = exchange
                .getRequest()
                .getHeaders()
                .getFirst(HttpHeaders.AUTHORIZATION);

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            return authHeader.substring(7);
        }

        return authHeader;
    }
}

