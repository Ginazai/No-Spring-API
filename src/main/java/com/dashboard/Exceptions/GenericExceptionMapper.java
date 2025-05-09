package com.dashboard.Exceptions;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import javax.json.Json;
import javax.json.JsonObject;

@Provider
public class GenericExceptionMapper implements ExceptionMapper<Throwable> {

    @Override
    public Response toResponse(Throwable exception) {
        JsonObject error = Json.createObjectBuilder()
                .add("error", exception.getClass().getSimpleName())
                .add("message", exception.getMessage() != null ? exception.getMessage() : "Internal server error")
                .build();

        return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity(error)
                .type(MediaType.APPLICATION_JSON)
                .build();
    }
}

