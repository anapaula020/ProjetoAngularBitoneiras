package br.unitins.tp1.resource;

import jakarta.annotation.security.RolesAllowed;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.SecurityContext;

import java.security.Principal;

@Path("/users")
@Produces(MediaType.APPLICATION_JSON)
@RequestScoped
public class UserResource {

    @Inject
    @Context
    SecurityContext securityContext;

    @GET
    @Path("/me")
    @RolesAllowed({"USER", "ADMIN"})
    public UserInfoResponse getMyUserInfo() {
        Principal principal = securityContext.getUserPrincipal();

        if (principal != null) {
            boolean isAdmin = securityContext.isUserInRole("ADMIN");
            boolean isUser = securityContext.isUserInRole("USER");
            return new UserInfoResponse(principal.getName(), isAdmin, isUser, "Você já está logado.");
        } else {
            return new UserInfoResponse(null, false, false, "Not authenticated.");
        }
    }

    public static class UserInfoResponse {
        public String username;
        public boolean isAdmin;
        public boolean isUser;
        public String message;

        public UserInfoResponse(String username, boolean isAdmin, boolean isUser, String message) {
            this.username = username;
            this.isAdmin = isAdmin;
            this.isUser = isUser;
            this.message = message;
        }
    }
}