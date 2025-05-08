package com.dashboard.REST;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.dashboard.Entities.User;
import com.dashboard.Services.UserService;
import com.dashboard.util.JwtUtil;
import com.dashboard.util.PasswordUtil;

@Path("/")
@Produces(MediaType.APPLICATION_JSON)
@ApplicationScoped
public class MainResource {
	@Inject
    private UserService userService;
	
	@POST
    @Path("/login")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response login(User credentials) {
        User user = userService.authenticate(credentials);
        if (user == null || !PasswordUtil.verifyPassword(credentials.getPassword(), user.getPassword())) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }

        boolean isAdmin = user.getRoles().stream()
            .anyMatch(role -> "ROLE_ADMIN".equals(role.getName()));
        if (isAdmin) {
            String token = JwtUtil.generateToken(user);
            return Response.ok().header(HttpHeaders.AUTHORIZATION, "Bearer " + token).build();
        }
        return Response.status(Response.Status.FORBIDDEN).build();
    }
}
