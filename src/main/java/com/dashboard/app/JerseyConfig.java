package com.dashboard.app;

import javax.ws.rs.ApplicationPath;

import org.glassfish.jersey.server.ResourceConfig;

import com.dashboard.util.JWTFilter;
import com.dashboard.Exceptions.*;

@ApplicationPath("/")
public class JerseyConfig extends ResourceConfig {
    public JerseyConfig() {
    	packages("com.dashboard.REST", "com.dashboard.util", "com.dashboard.Exceptions");
        register(JWTFilter.class);
        register(GenericExceptionMapper.class);
        register(NotFoundExceptionMapper.class);
    }
}