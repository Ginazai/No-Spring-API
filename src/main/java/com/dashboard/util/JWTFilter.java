package com.dashboard.util;

import io.jsonwebtoken.*;
import javax.annotation.Priority;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.*;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;

import org.hibernate.annotations.common.util.impl.LoggerFactory;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

@Provider
@Priority(Priorities.AUTHENTICATION)
public class JWTFilter implements ContainerRequestFilter {
	private static final Logger logger = Logger.getLogger(JWTFilter.class.getName());
	
    private static final String AUTH_HEADER = "Authorization";
    private static final String BEARER_PREFIX = "Bearer ";
    private static final String SECRET_KEY = "clave_secreta_de_128bits_de_longitud"; // Cambia esto por tu clave secreta
    static {
        try {
            // Crear nombre del archivo con fecha/hora
            String fecha = new SimpleDateFormat("yyyy-MM-dd_HH").format(new Date());
            String nombreArchivo = "../logs/auth-log-" + fecha + ".log";

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
    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
    	String path = requestContext.getUriInfo().getPath();
    	// Permitir acceso libre a ciertas rutas
        if (!path.contains("usuarios")) {
            return;
        }
        String authHeader = requestContext.getHeaderString(AUTH_HEADER);
        System.out.println("Authorization Header: " + authHeader);

        if (authHeader == null || !authHeader.startsWith(BEARER_PREFIX)) {
        	logger.severe("Error: Header no contiene \"Bearer\" o es nulo");
            requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());
            return;
        }

        String token = authHeader.substring(BEARER_PREFIX.length());
        try {
            JwtParser jwtParser = Jwts.parserBuilder()
                    .setSigningKey(SECRET_KEY.getBytes())
                    .build();
            jwtParser.parseClaimsJws(token);
        } catch (JwtException e) {
            logger.severe("Error: Invalid token: " + e.getMessage());
            requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());
        }
    }

}
