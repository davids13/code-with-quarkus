package jwt;

import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.SecurityContext;
import org.eclipse.microprofile.jwt.JsonWebToken;

@Path("secured")
@RequestScoped
public class SecuredResource {

    JsonWebToken jwt;
    @Context
    SecurityContext ctx;

    @Inject
    public SecuredResource(JsonWebToken jwt) {
        this.jwt = jwt;
    }

    @GET
    @Path("hello")
    @RolesAllowed({"user", "admin"})
    @Produces(MediaType.TEXT_PLAIN)
    public String hello() {
        String name = jwt.getName();
        boolean isAdmin = jwt.getGroups().contains("admin");
        return "Hello " + name + "! Roles: " + jwt.getGroups() + (isAdmin ? " You're an admin." : "");
    }

    @GET
    @Path("public")
    @PermitAll
    @Produces(MediaType.TEXT_PLAIN)
    public String publicInfo() {
        if (ctx.getUserPrincipal() != null)
            return "Hello " + ctx.getUserPrincipal().getName() + "! (token detected)";

        return "This is a public endpoint, Guest";
    }
}
