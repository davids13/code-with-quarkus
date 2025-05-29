package acme;

import entity.Fruit;
import entity.Item;
import error.ErrorResponse;
import error.ItemNotFoundException;
import error.MyCustomApplicationException;
import filters.Audited;
import io.smallrye.mutiny.Uni;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.NewCookie;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;
import org.jboss.logging.Logger;

import java.net.URI;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Path("/hello")
public class GreetingResource {

    public static final Logger LOG = Logger.getLogger(GreetingResource.class);

    private static final Map<String, Item> items = new ConcurrentHashMap<>();

    static {
        items.put("1", new Item("1", "Sample Item", "This is a sample item."));
    }

    /**
     * only global filters run
     *
     * @return String
     */
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String hello() {
        LOG.info("Executing standard hello() method.");
        return "Hello from Quarkus REST";
    }


    /**
     * global filters + audit filter will display
     *
     * @return String
     */
    @GET
    @Path("audited")
    @Produces(MediaType.TEXT_PLAIN)
    @Audited
    public String helloAudited() {
        LOG.info("Executing AUDITED helloAudited() method.");
        return "Hello from the *audited* endpoint!";
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createItem(final Item item, @Context UriInfo uriInfo) {
        // ID generation
        item.setId(UUID.randomUUID().toString());
        items.put(item.getId(), item);

        // Build URI for the newly created resource for the Location header
        URI location = uriInfo.getAbsolutePathBuilder().path(item.getId()).build();

        // 201 Created with location header and the created item in the body
        return Response.created(location).entity(item).build();
    }

    @GET
    @Path("empty")
    public Response getEmpty() {
        return Response.noContent().build();
    }

    @GET
    @Path("itemById/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getItemById(@PathParam("id") final String id) {
        ErrorResponse error = new ErrorResponse("404_NOT_FOUND", "Item with id: " + id + " not found.");
        Item item = items.get(id);
        if (item != null) {
            return Response.ok(item).build();
        }
        return Response.status(Response.Status.NOT_FOUND).entity(error).build();
    }

    @GET
    @Path("withCustomHeaders")
    public Response getItemWithCustomHeaders() {
        Item item = new Item("123", "Header Item", "Item with custom");
        return Response.ok(item)
                .header("X-Custom-Header", "MyValue")
                .header("X-Another-Header", "AnotherCoolValue")
                .cookie(new NewCookie.Builder("session-id")
                        // setting cookie
                        .value("abc")
                        .path("/items")
                        .domain("localhost") // be careful with domain in real apps
                        .maxAge(3600) // 1h
                        .secure(false) // true in production env
                        .httpOnly(true)
                        .build()
                ).build();
    }

    @GET
    @Path("mappedError/{id}")
    public Response getItemByIdWithMappedException(@PathParam("id") final String id) {
        Item item = items.get(id);

        if (item != null) {
            return Response.ok(item).build();
        } else {
            throw new ItemNotFoundException(id);
        }
    }

    @GET
    @Path("error")
    public String cause500() {
        throw new RuntimeException("Simulated failure!");
    }

    @GET
    @Path("api/error")
    @Produces(MediaType.APPLICATION_JSON)
    public String apiError() {
        throw new RuntimeException("Simulated API error!");
    }

    @GET
    @Path("custom-error")
    @Produces(MediaType.APPLICATION_JSON)
    public String triggerCustomError() {
        throw new MyCustomApplicationException("This is a test of the custom application exception");
    }

    @GET
    @Path("mutiny/{id}")
    public Uni<Response> getById(Long id) {
        return Fruit.findById(id);


    }
}
