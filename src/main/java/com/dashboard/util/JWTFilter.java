package com.dashboard.util;

import io.jsonwebtoken.*;
import javax.annotation.Priority;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.*;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;

import com.dashboard.app.Secured;

import java.io.IOException;

@Provider
@Secured
@Priority(Priorities.AUTHENTICATION)
public class JWTFilter implements ContainerRequestFilter {

    private static final String AUTH_HEADER = "Authorization";
    private static final String BEARER_PREFIX = "Bearer ";
    private static final String SECRET_KEY = "clave_secreta123";

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        String authHeader = requestContext.getHeaderString(AUTH_HEADER);
        if (authHeader == null || !authHeader.startsWith(BEARER_PREFIX)) {
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
            requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());
        }
    }
}
