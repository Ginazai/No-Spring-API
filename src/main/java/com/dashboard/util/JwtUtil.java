package com.dashboard.util;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.stream.Collectors;
import javax.crypto.SecretKey;
import com.dashboard.Entities.Role;
import com.dashboard.Entities.User;

public class JwtUtil {
    public static String SECRET_KEY = "clave-secreta";
    
    private static SecretKey SECRET_KEY_ENC = Keys.hmacShaKeyFor(SECRET_KEY.getBytes(StandardCharsets.UTF_8));
    
    public static String generateToken(User user) {
        return Jwts.builder()
            .setSubject(user.getUsername())
            .claim("roles", user.getRoles().stream().map(Role::getName).collect(Collectors.toList()))
            .setIssuedAt(new Date())
            .setExpiration(new Date(System.currentTimeMillis() + 600_000)) // 10 minutos
            .signWith(SECRET_KEY_ENC, SignatureAlgorithm.HS256)
            .compact();
    }

    public static Claims validateToken(String token) throws JwtException {
        try {
            return Jwts.parserBuilder()
                .setSigningKey(SECRET_KEY_ENC)
                .build()
                .parseClaimsJws(token)
                .getBody();
        } catch (ExpiredJwtException e) {
            throw new JwtException("Token has expired", e);
        } catch (UnsupportedJwtException e) {
            throw new JwtException("Unsupported token", e);
        } catch (MalformedJwtException e) {
            throw new JwtException("Malformed token", e);
        } catch (SignatureException e) {
            throw new JwtException("Invalid token signature", e);
        } catch (IllegalArgumentException e) {
            throw new JwtException("Token is null or empty", e);
        }
    }

}


