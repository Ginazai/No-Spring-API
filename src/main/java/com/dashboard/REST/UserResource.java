package com.dashboard.REST;

import java.util.Set;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.dashboard.Entities.CredentialDTO;
import com.dashboard.Entities.Role;
import com.dashboard.Entities.User;
import com.dashboard.Services.AuthService;
import com.dashboard.Services.UserService;
import com.dashboard.app.Secured;
import com.dashboard.util.JwtUtil;
import com.dashboard.util.PasswordUtil;

@Path("/")
@Produces(MediaType.APPLICATION_JSON)
@ApplicationScoped
public class UserResource {

    @Inject
    private UserService userService;
    
    @Inject
    private AuthService authService;

    @GET
    @Secured
    @Path("/usuarios")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUsers() {
        return Response.ok(userService.listarUsuarios()).build();
    }
    
    @GET
    @Secured
    @Path("/usuarios/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response getOneUser(@PathParam("id") Long id) {
        return Response.ok(userService.buscarPorId(id)).build();
    }
    
    @POST
    @Secured
    @Path("/usuarios")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createUsuario(User u) {
        return Response.ok(userService.guardar(u)).build();
    }
    
    @PUT
    @Secured
    @Path("/usuarios/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateUser(@PathParam("id") Long id, User u) {
        return Response.ok(userService.actualizarUsuario(id, u)).build();
    }
    
    @PUT
    @Secured
    @Path("/usuarios/{id}/roles")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateRoles(@PathParam("id") Long id, Set<String> roles) {
        return Response.ok(userService.actualizarRoles(id, roles)).build();
    }
    
    @DELETE
    @Secured
    @Path("/usuarios/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteUser(@PathParam("id") Long id) {
        return Response.ok(userService.borrarUsuario(id)).build();
    }
    @POST
    @Path("/registro")
    public Response registrar(CredentialDTO cred) {
        authService.registrar(cred.getUsername(), cred.getPassword());
        return Response.status(Response.Status.CREATED).build();
    }

    @POST
    @Path("/login")
    public Response login(CredentialDTO cred) {
        String token = authService.login(cred.getUsername(), cred.getPassword());
        if (token == null) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
        return Response.ok().header("Authorization", "Bearer " + token).build();
    }
}
