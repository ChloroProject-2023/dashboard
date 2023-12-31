package com.usth.edu.vn.Resource;

import com.usth.edu.vn.repository.UsersRepository;
import io.quarkus.security.identity.SecurityIdentity;
import jakarta.annotation.security.RolesAllowed;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;

@Path("/")
@ApplicationScoped
@Produces(MediaType.TEXT_PLAIN)
public class TestResource {

    @Inject
    UsersRepository usersRepository;

    @GET
    @Path("hello")
    @RolesAllowed("user")
    public Response hello() {
        String hello = "Hello from JWT service!";
        return Response.ok(hello).build();
    }

    @GET
    @Path("role")
    @RolesAllowed({"admin", "user"})
    public Response role(@Context SecurityContext securityContext) {
        return usersRepository
                .find("username", securityContext.getUserPrincipal().getName())
                .singleResultOptional()
                .map(u -> Response.ok(u.getRoles()).build())
                .orElse(Response.status(Response.Status.BAD_REQUEST).build());
    }

    @GET
    @Path("role2")
    @RolesAllowed({"admin", "user"})
    public Response role2(@Context SecurityIdentity securityIdentity) {
        return Response.ok(securityIdentity.getRoles()).build();
    }
}
