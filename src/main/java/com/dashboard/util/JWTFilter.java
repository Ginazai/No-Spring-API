package com.dashboard.util;

import io.jsonwebtoken.*;
import javax.annotation.Priority;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.*;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;
import java.io.IOException;

@Provider
@Priority(Priorities.AUTHENTICATION)
public class JWTFilter implements ContainerRequestFilter {

    private static final String AUTH_HEADER = "Authorization";
    private static final String BEARER_PREFIX = "Bearer ";
    private static final String SECRET_KEY = "clave_secreta_de_128bits_de_longitud"; // Cambia esto por tu clave secreta

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
            System.out.println("Authorization header is missing or invalid");
            requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());
            return;
        }

        String token = authHeader.substring(BEARER_PREFIX.length());
        try {
            JwtParser jwtParser = Jwts.parserBuilder()
                    .setSigningKey(SECRET_KEY.getBytes())
                    .build();
            jwtParser.parseClaimsJws(token);
            System.out.println("Token is valid");
        } catch (JwtException e) {
            System.out.println("Invalid token: " + e.getMessage());
            requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());
        }
    }

}
