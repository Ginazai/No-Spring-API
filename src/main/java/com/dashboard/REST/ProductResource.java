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
import com.dashboard.Entities.Product;
import com.dashboard.Entities.Role;
import com.dashboard.Services.ProductService;

@Path("/productos")
@Produces(MediaType.APPLICATION_JSON)
@ApplicationScoped
public class ProductResource {
	
	 @Inject
    private ProductService productService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getProducts() {
        return Response.ok(productService.listarProductos()).build();
    }
    
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getOneProduct(@PathParam("id") Long id) {
        return Response.ok(productService.buscarPorId(id)).build();
    }
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createProduct(Product product) {
        return Response.ok(productService.guardar(product)).build();
    }
    
    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateProduct(@PathParam("id") Long id, Product product) {
        return Response.ok(productService.actualizarProducto(id, product)).build();
    }
    
    @PUT
    @Path("/{id}/categorias")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateCategories(@PathParam("id") Long id, Set<String> categorias) {
        return Response.ok(productService.actualizarRoles(id, categorias)).build();
    }
    
    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteProduct(@PathParam("id") Long id) {
        return Response.ok(productService.borrarProducto(id)).build();
    }
}
