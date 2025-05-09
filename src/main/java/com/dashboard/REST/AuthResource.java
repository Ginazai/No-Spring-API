package com.dashboard.REST;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.dashboard.Entities.CredentialDTO;
import com.dashboard.Entities.RegisterDTO;
import com.dashboard.Services.AuthService;
@Path("/")
@Produces(MediaType.APPLICATION_JSON)
@ApplicationScoped
public class AuthResource {
	@Inject
	AuthService authService;
	
	@POST
    @Path("/registro")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response registrar(RegisterDTO cred) {
        return Response.ok(authService.registrar(cred.getName(), 
        		cred.getUsername(), 
        		cred.getPassword()))
        		.build();
    }
    @POST
    @Path("/login")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response login(CredentialDTO cred) {
        if (authService == null) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("AuthService is not initialized").build();
        }
        String token = authService.login(cred.getUsername(), cred.getPassword());
        if (token == null) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
        return Response.ok(token).header("Authorization", "Bearer " + token).build();
    }
}
