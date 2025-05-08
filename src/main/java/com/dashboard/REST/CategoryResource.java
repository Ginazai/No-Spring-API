package com.dashboard.REST;

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
import com.dashboard.Entities.ProductCategory;
import com.dashboard.Services.CategoryService;

@Path("/categorias")
@Produces(MediaType.APPLICATION_JSON)
@ApplicationScoped
public class CategoryResource {
	
	 @Inject
    private CategoryService categoryService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCategories() {
        return Response.ok(categoryService.listarCategorias()).build();
    }
    
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getOneCategory(@PathParam("id") Long id) {
        return Response.ok(categoryService.buscarPorId(id)).build();
    }
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createCategory(ProductCategory category) {
        return Response.ok(categoryService.guardar(category)).build();
    }
    
    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateCategory(@PathParam("id") Long id, ProductCategory category) {
        return Response.ok(categoryService.actualizarCategoria(id, category)).build();
    }
    
    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteCategory(@PathParam("id") Long id) {
        return Response.ok(categoryService.borrarCategoria(id)).build();
    }
}
