package com.springboot.job_platform.services;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.SecretKey;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import com.springboot.job_platform.models.User;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;

@Service

public class JwtService {
    @Value("${jwt.key}")
    private String key;

    public String generateToken(User user) {
        return Jwts
            .builder()
            .subject(user.getEmail())
            .claim("id", user.getId())
            .claim("name", user.getName())
            .claim("surname", user.getSurname())
            .claim("role", user.getRole().getName())
            .issuedAt(new Date())
            .expiration(new Date(System.currentTimeMillis() + 1000 * 3600))
            .signWith(getKey())
            .compact();
    }

    public String extractEmail(String token) {
        try {
            return Jwts
            .parser()
            .verifyWith(getKey())
            .build()
            .parseSignedClaims(token)
            .getPayload()
            .getSubject();
        }
        catch(ExpiredJwtException e) {
            System.out.println("Token expired: " + e.getMessage());
        }
        catch(SignatureException e) {
            System.out.println("Inavlid token: " + e.getMessage());
        }
        catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }

        return null;
    }

    public String updateToken(User user, String token) {
        Claims claims = Jwts
            .parser()
            .verifyWith(getKey())
            .build()
            .parseSignedClaims(token)
            .getPayload();

        Map<String, Object> claimsCopy = new HashMap<String, Object>(claims);
        claimsCopy.put("sub", user.getEmail());
        claimsCopy.put("name", user.getName());
        claimsCopy.put("surname", user.getSurname());

        return Jwts
            .builder()
            .claims(claimsCopy)
            .signWith(getKey())
            .compact();
    }

    public Boolean verifyToken(UserDetails userDetails, String token) {
        String email = userDetails.getUsername();

        return (extractEmail(token).equals(email) && !isTokenExpired(token));
    }

    private Boolean isTokenExpired(String token) {
        try {
            return Jwts
                .parser()
                .verifyWith(getKey())
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getExpiration()
                .before(new Date());
        }
        catch(ExpiredJwtException e) {
            System.out.println("Token expired: " + e.getMessage());
        }
        catch(SignatureException e) {
            System.out.println("Inavlid token: " + e.getMessage());
        }
        catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }

        return true;
    }

    private SecretKey getKey() {
        return Keys.hmacShaKeyFor(key.getBytes());
    }
}
