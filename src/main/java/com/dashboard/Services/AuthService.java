package com.dashboard.Services;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.*;

import com.dashboard.Entities.User;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.util.Date;

@Stateless
public class AuthService {

    private static final String SECRET_KEY = "clave_secreta123";
    private static final long EXPIRATION = 10 * 60 * 1000; // 10 mins

    @PersistenceContext
    private EntityManager em;

    public void registrar(String username, String password) {
        User usuario = new User();
        usuario.setUsername(username);
        usuario.setPassword(password); // En producción, usa hashing (ej. BCrypt)
        em.persist(usuario);
    }

    public String login(String username, String password) {
        User usuario = em.createQuery("SELECT u FROM User u WHERE u.username = :user", User.class)
                .setParameter("user", username)
                .getResultList()
                .getFirst();
        if (usuario == null || !usuario.getPassword().equals(password)) {
            return null;
        }

        // Create a SecretKey object from the secret string
        Key secretKey = new SecretKeySpec(SECRET_KEY.getBytes(), SignatureAlgorithm.HS256.getJcaName());

        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION))
                .signWith(secretKey, SignatureAlgorithm.HS256)
                .compact();
    }
}
