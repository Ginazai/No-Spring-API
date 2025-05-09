package com.dashboard.Exceptions;

import javax.json.Json;
import javax.json.JsonObject;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

public class NotAuthorizedExceptionMapper implements ExceptionMapper<javax.ws.rs.NotAuthorizedException> {
	@Override
    public Response toResponse(javax.ws.rs.NotAuthorizedException ex) {
        JsonObject error = Json.createObjectBuilder()
				.add("error", "No estas autorizado para acceder a este recurso.")
				.add("message", ex.getMessage())
				.build();

        return Response.status(Response.Status.NOT_FOUND)
                .entity(error)
                .type(MediaType.APPLICATION_JSON)
                .build();
    }
}
