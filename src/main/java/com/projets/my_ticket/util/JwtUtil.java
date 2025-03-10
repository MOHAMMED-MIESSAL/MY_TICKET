package com.projets.itsupportticket.util;

import com.projets.itsupportticket.domain.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Base64;
import java.util.Date;

@Component
public class JwtUtil {
    // Clé secrète statique
    private static final String SECRET = "YWJjMTIzIT8kKiYoKS1fQGFiY2RlZmdoaWprbG1ub3A=";
    private static final Key secretKey = Keys.hmacShaKeyFor(Base64.getDecoder().decode(SECRET));

    private final long expirationTime = 86400000; // 24 heures

    public String generateToken(User user) {
        return Jwts.builder()
                .setSubject(String.valueOf(user.getId())) // Ajouter l'ID de l'utilisateur
                .claim("role", user.getRole()) // Ajouter le rôle de l'utilisateur
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expirationTime))
                .signWith(secretKey)
                .compact();
    }

    public String validateToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public String extractRole(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .get("role", String.class); // Extraire le rôle du token
    }
}
