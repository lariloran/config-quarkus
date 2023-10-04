package org.acme;

import java.util.Optional;

import org.eclipse.microprofile.config.inject.ConfigProperty;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/hello")
public class GreetingResource {

@ConfigProperty(name = "ifrs.pw2",  defaultValue="" )
String message;

    @GET

    @Produces(MediaType.TEXT_PLAIN)
    public String config() {
    return message;
}
}


