package dto;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
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

//@Path("posts")
@ApplicationScoped
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PostResource {

    // COMMENTED TO USE OTHERS DATABASE INSTEAD H2

    PostMapper mapper;

    /*@POST
    @Transactional
    public Response create(final CreatePostDto dto) {
        Post post = mapper.toEntity(dto);
        post.persist();
        return Response.status(Response.Status.CREATED).entity(mapper.toDto(post)).build();
    }

    @GET
    public List<PostDto> list() {
        return mapper.toDtoList(Post.listAll());
    }

    @GET
    @Path("{id}")
    public Response get(@PathParam("id") final Long id) {
        Post post = Post.findById(id);
        if (post == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(mapper.toDto(post)).build();
    }

    @PUT
    @Path("/{id}")
    @Transactional
    public Response update(@PathParam("id") Long id, UpdatePostDto dto) {
        Post post = Post.findById(id);
        if (post == null) return Response.status(Response.Status.NOT_FOUND).build();
        mapper.updateEntityFromDto(dto, post);
        return Response.ok(mapper.toDto(post)).build();
    }

    @DELETE
    @Path("/{id}")
    @Transactional
    public Response delete(@PathParam("id") Long id) {
        Post post = Post.findById(id);
        if (post == null) return Response.status(Response.Status.NOT_FOUND).build();
        post.delete();
        return Response.noContent().build();
    }*/
}
