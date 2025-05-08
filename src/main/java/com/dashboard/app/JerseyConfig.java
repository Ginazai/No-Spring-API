package com.dashboard.app;

import javax.ws.rs.ApplicationPath;

import org.glassfish.jersey.server.ResourceConfig;

import com.dashboard.util.JWTFilter;

@ApplicationPath("/")
public class JerseyConfig extends ResourceConfig {
    public JerseyConfig() {
        packages("com.dashboard.REST");
        register(JWTFilter.class);
    }
}

