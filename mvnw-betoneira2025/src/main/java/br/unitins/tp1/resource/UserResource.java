// src/main/java/br/unitins/tp1/resource/UserResource.java
package br.unitins.tp1.resource;

import jakarta.annotation.security.RolesAllowed; // Import for role-based access control
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.SecurityContext;

import java.security.Principal;

@Path("/users") // Base path for user-related endpoints
@Produces(MediaType.APPLICATION_JSON)
@RequestScoped // Define the scope of this bean
public class UserResource {

    @Inject
    @Context // Inject the SecurityContext to get information about the authenticated user
    SecurityContext securityContext;

    @GET
    @Path("/me") // Specific path for getting info about the current user
    @RolesAllowed({"USER", "ADMIN"}) // This endpoint requires either USER or ADMIN role to access
    public UserInfoResponse getMyUserInfo() {
        Principal principal = securityContext.getUserPrincipal(); // Get the authenticated user's principal

        if (principal != null) {
            // The getRolesAsSet() is from your Cliente model,
            // but SecurityContext provides isUserInRole for generic checks.
            // For more detailed role info or custom user data, you'd fetch the Cliente entity.

            boolean isAdmin = securityContext.isUserInRole("ADMIN");
            boolean isUser = securityContext.isUserInRole("USER");

            // You might want to fetch the Cliente entity here to get more details like ID
            // Cliente cliente = clienteRepository.findByEmail(principal.getName());
            // This example simplifies by just showing what's available from SecurityContext.

            return new UserInfoResponse(
                principal.getName(), // Usually the email (UPN)
                isAdmin,
                isUser,
                "You are successfully logged in!"
            );
        } else {
            // This should ideally not be reached if @RolesAllowed is working,
            // as unauthenticated users would be denied before reaching here.
            return new UserInfoResponse(null, false, false, "Not authenticated.");
        }
    }

    // A simple DTO to return user information
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