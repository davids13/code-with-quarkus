package error;

import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import org.jboss.logging.Logger;

import java.util.UUID;

public class MyCustomApplicationExceptionMapper implements ExceptionMapper<MyCustomApplicationException> {

    private static final Logger LOG = Logger.getLogger(MyCustomApplicationExceptionMapper.class);

    @Override
    public Response toResponse(MyCustomApplicationException exception) {
        LOG.warnf("Handling MyCustomApplicationException: %s", exception.getMessage());

        // Example: Return JSON response for this custom error
        GenericExceptionMapper.ErrorResponse errorResponse = new GenericExceptionMapper.ErrorResponse(
                "custom-" + UUID.randomUUID().toString().substring(0, 8),
                "A custom application error occurred: " + exception.getMessage(),
                Response.Status.BAD_REQUEST.getStatusCode(), // Or any other appropriate status
                exception.getClass().getSimpleName(),
                exception.getMessage());

        return Response.status(Response.Status.BAD_REQUEST)
                .entity(errorResponse)
                .type(MediaType.APPLICATION_JSON) // Default to JSON for this example
                .build();
    }
}
