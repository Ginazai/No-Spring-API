package com.dashboard.Exceptions;

import javax.json.Json;
import javax.json.JsonObject;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class NotFoundExceptionMapper implements ExceptionMapper<javax.ws.rs.NotFoundException> {

    @Override
    public Response toResponse(javax.ws.rs.NotFoundException ex) {
        JsonObject error = Json.createObjectBuilder()
				.add("error", "El recurso solicitado no fue encontrado.")
				.add("message", ex.getMessage())
				.build();

        return Response.status(Response.Status.NOT_FOUND)
                .entity(error)
                .type(MediaType.APPLICATION_JSON)
                .build();
    }
}

