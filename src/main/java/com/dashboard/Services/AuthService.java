package com.dashboard.Services;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.*;

import org.json.JSONObject;

import com.dashboard.Entities.User;
import com.dashboard.util.JWTFilter;
import com.dashboard.util.PasswordUtil;

import javax.crypto.spec.SecretKeySpec;

import java.io.IOException;
import java.security.Key;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

@Stateless
public class AuthService {
    @PersistenceContext(unitName = "myUnit")
    private EntityManager em;
    @Inject
    PasswordUtil passwordUtil;
    
    private static final Logger logger = Logger.getLogger(JWTFilter.class.getName());
    static {
        try {
            // Crear nombre del archivo con fecha/hora
            String fecha = new SimpleDateFormat("yyyy-MM-dd_HH").format(new Date());
            String nombreArchivo = "auth-log-" + fecha + ".log";

            // Crear el FileHandler
            FileHandler fileHandler = new FileHandler(nombreArchivo, true);
            fileHandler.setFormatter(new SimpleFormatter());

            // Asignar el nuevo handler
            logger.addHandler(fileHandler);
            logger.setLevel(Level.ALL);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Error al inicializar el logger", e);
        }
    }

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
        logger.info("Usuario registrado con éxito!");
        return json.toString();
    }

    public String login(String username, String password) {
        List<User> usuarios = em.createQuery("SELECT u FROM User u WHERE u.username = :user", User.class)
                .setParameter("user", username)
                .getResultList();
        if (usuarios.isEmpty()) {
        	JSONObject json = new JSONObject();
        	json.put("error", "No user found with username: " + username);
        	logger.severe("Error: No user found with username: " + username);
            return json.toString();
        }

        User usuario = usuarios.get(0);
        if (!PasswordUtil.verifyPassword(password, usuario.getPassword())) {
        	JSONObject json = new JSONObject();
        	json.put("error", "Password does not match");
        	logger.severe("Error: Password does not match");
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
