package com.tody.dayori.auth.service;

import com.tody.dayori.exception.AppException;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Collections;
import java.util.Date;

@Component
@Slf4j
public class JwtProvider {
    private final Key key;

    public JwtProvider(@Value("${jwt.secret}") String secretKey) {
        this.key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretKey));
    }

    public String createToken(long userSeq) {
        Date now = new Date();
        Date validity = new Date(now.getTime() + 3_600_000);

        return Jwts.builder()
                .setIssuer(String.valueOf(userSeq))
                .setIssuedAt(now)
                .setExpiration(validity)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (SecurityException | MalformedJwtException e) {
            throw new AppException("Invalid JWT Token", HttpStatus.UNAUTHORIZED);
        } catch (ExpiredJwtException e) {
            throw new AppException("Expired JWT Token", HttpStatus.UNAUTHORIZED);
        } catch (UnsupportedJwtException e) {
            throw new AppException("Unsupported JWT Token", HttpStatus.UNAUTHORIZED);
        } catch (IllegalArgumentException e) {
            throw new AppException("JWT claims string is empty.", HttpStatus.UNAUTHORIZED);
        }
    }
    public Authentication getAuthentication(String accessToken) {
        Claims claims = parseClaims(accessToken);
        return new UsernamePasswordAuthenticationToken(claims.getIssuer(), null, Collections.emptyList());
    }

    private Claims parseClaims(String accessToken) {
        try {
            return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(accessToken).getBody();
        } catch (ExpiredJwtException e) {
            throw new AppException("Expired JWT Token", HttpStatus.UNAUTHORIZED);
        }
    }
}
