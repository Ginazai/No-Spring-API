package com.dashboard.Services;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.*;

import org.json.JSONObject;

import com.dashboard.Entities.User;
import com.dashboard.util.PasswordUtil;

import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Stateless
public class AuthService {
    @PersistenceContext(unitName = "myUnit")
    private EntityManager em;
    @Inject
    PasswordUtil passwordUtil;
    
    private static final String SECRET_KEY = "clave_secreta_de_128bits_de_longitud";
    private static final long EXPIRATION = 10 * 60 * 1000; // 10 mins

    public String registrar(String name, String username, String password) {
        User usuario = new User();
        usuario.setName(name);
        usuario.setUsername(username);
        usuario.setPassword(PasswordUtil.hashPassword(password));
        usuario.setCreate_date(LocalDateTime.now());
        usuario.setLast_access(LocalDateTime.now());
        usuario.setActive(true);
        em.persist(usuario);
        JSONObject json = new JSONObject();
        json.put("message", "Usuario registrado con éxito!");
        return json.toString();
    }

    public String login(String username, String password) {
        List<User> usuarios = em.createQuery("SELECT u FROM User u WHERE u.username = :user", User.class)
                .setParameter("user", username)
                .getResultList();
        if (usuarios.isEmpty()) {
        	JSONObject json = new JSONObject();
        	json.put("error", "No user found with username: " + username);
            return json.toString();
        }

        User usuario = usuarios.get(0);
        if (!PasswordUtil.verifyPassword(password, usuario.getPassword())) {
        	JSONObject json = new JSONObject();
        	json.put("error", "Password does not match");
            return json.toString();
        }

        Key secretKey = new SecretKeySpec(SECRET_KEY.getBytes(), SignatureAlgorithm.HS256.getJcaName());
        String token = Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION))
                .signWith(secretKey, SignatureAlgorithm.HS256)
                .compact();
        JSONObject json = new JSONObject();
        json.put("token", token);
        return json.toString();
    }
}
