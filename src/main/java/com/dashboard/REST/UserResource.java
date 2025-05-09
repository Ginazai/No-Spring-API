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
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import com.dashboard.Entities.User;
import com.dashboard.Services.UserService;
import com.dashboard.app.Secured;

@Path("/usuarios")
@Produces(MediaType.APPLICATION_JSON)
@ApplicationScoped
public class UserResource {

    @Inject
    private UserService userService;

    @GET
    @Secured
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUsers() {
        return Response.ok(userService.listarUsuarios()).build();
    }
    
    @GET
    @Secured
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response getOneUser(@PathParam("id") Long id) {
        return Response.ok(userService.buscarPorId(id)).build();
    }
    
    @POST
    @Secured
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createUsuario(User u) {
        return Response.ok(userService.guardar(u)).build();
    }
    
    @PUT
    @Secured
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateUser(@PathParam("id") Long id, User u) {
        return Response.ok(userService.actualizarUsuario(id, u)).build();
    }
    
    @PUT
    @Secured
    @Path("/{id}/roles")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateRoles(@PathParam("id") Long id, Set<String> roles) {
        return Response.ok(userService.actualizarRoles(id, roles)).build();
    }
    
    @DELETE
    @Secured
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteUser(@PathParam("id") Long id) {
        return Response.ok(userService.borrarUsuario(id)).build();
    }
}
