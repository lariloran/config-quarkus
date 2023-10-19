package org.acme;


import org.eclipse.microprofile.faulttolerance.CircuitBreaker;
import org.eclipse.microprofile.faulttolerance.Fallback;
import org.jboss.resteasy.reactive.server.jaxrs.ResponseBuilderImpl;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/")
public class FaultTolerance {

  private static final String FALL_BACK_MESSAGE = "FallbackMethod: ";

    @GET
    @Path("/fallback/{name}")
    @Produces(MediaType.TEXT_PLAIN)
    @Fallback(fallbackMethod = "recover")
    public String getName(@PathParam("name") String name) {
        if (name.equalsIgnoreCase("error")) {
            ResponseBuilderImpl builder = new ResponseBuilderImpl();
            builder.status(Response.Status.INTERNAL_SERVER_ERROR);
            builder.entity("The requested was an error");
            Response response = builder.build();
            throw new WebApplicationException(response);
        }

        return name;
    }
    public String recover(String name) {
        return FALL_BACK_MESSAGE + name;
    }

    @GET
    @Path("/circuit/{name}")
    @Produces(MediaType.TEXT_PLAIN)
    @CircuitBreaker(requestVolumeThreshold = 10)
    public String getNotName(@PathParam("name") String name) {
        if (name.equalsIgnoreCase("error")) {
            ResponseBuilderImpl builder = new ResponseBuilderImpl();
            builder.status(Response.Status.INTERNAL_SERVER_ERROR);
            builder.entity("Circuito fechou");
            Response response = builder.build();
            throw new WebApplicationException(response);
        }

        return name;
    }

}


