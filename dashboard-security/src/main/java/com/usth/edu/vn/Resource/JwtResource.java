package com.usth.edu.vn.Resource;

import com.usth.edu.vn.Jwt.JwtIssuerType;
import com.usth.edu.vn.Jwt.JwtService;

import io.quarkus.security.identity.SecurityIdentity;
import jakarta.annotation.security.RolesAllowed;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.Set;

@Path("jwt")
@ApplicationScoped
@Produces(MediaType.TEXT_PLAIN)
public class JwtResource {

    @Inject
    JwtService jwtService;

    @Inject
    SecurityIdentity securityIdentity;

    @POST
    @RolesAllowed({"admin", "user"})
    public Response getToken() {
        String username = securityIdentity.getPrincipal().getName();
        Set<String> roles = securityIdentity.getRoles();
        String jwt = jwtService.generateJwt(JwtIssuerType.DASHBOARD_USER, username, roles);
        return Response.ok(jwt).build();
    }
}
