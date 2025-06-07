package tenant;

import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Path("api/todos")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@RolesAllowed("user")
public class TodoResource {

    // https://myfear.substack.com/p/quarkus-multi-tenant-todo-java-hibernate?utm_source=post-email-title&publication_id=4194688&post_id=164492050&utm_campaign=email-post-title&isFreemail=true&r=1om7hi&triedRedirect=true&utm_medium=email

    TodoService todoService;

    @Inject
    public TodoResource(TodoService todoService) {
        this.todoService = todoService;
    }

    @GET
    public List<Todo> getAll() {
        return todoService.getTodosForCurrentUser();
    }

    @GET
    @Path("/{id}")
    public Response getById(@PathParam("id") Long id) {
        return todoService.getTodoByIdForCurrentUser(id)
                .map(todo -> Response.ok(todo).build())
                .orElse(Response.status(Response.Status.NOT_FOUND).build());
    }

    @POST
    public Response create(Todo todoData) {
        if (todoData.title == null || todoData.title.isBlank()) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("{\"error\":\"Title cannot be empty\"}").build();
        }
        return Response.status(Response.Status.CREATED)
                .entity(todoService.createTodoForCurrentUser(todoData)).build();
    }

    @PUT
    @Path("/{id}")
    public Response update(@PathParam("id") Long id, Todo todoData) {
        if (todoData.title == null || todoData.title.isBlank()) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("{\"error\":\"Title cannot be empty\"}").build();
        }
        return todoService.updateTodoForCurrentUser(id, todoData)
                .map(todo -> Response.ok(todo).build())
                .orElse(Response.status(Response.Status.NOT_FOUND).build());
    }

    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") Long id) {
        return todoService.deleteTodoForCurrentUser(id)
                ? Response.noContent().build()
                : Response.status(Response.Status.NOT_FOUND).build();
    }
}
