package filters;

import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.ext.Provider;
import org.jboss.logging.Logger;

import java.io.IOException;

/**
 *  Logs every incoming request
 */
@Provider
public class GlobalLoggingRequestFilter implements ContainerRequestFilter {

    private static final Logger LOGGER = Logger.getLogger(GlobalLoggingRequestFilter.class.getName());

    @Override
    public void filter(ContainerRequestContext containerRequestContext) throws IOException {
        LOGGER.infof("[GLOBAL] Received request: %s %s", containerRequestContext.getMethod(),
                containerRequestContext.getUriInfo().getAbsolutePath());

        containerRequestContext.setProperty("request-start-time", System.nanoTime());
    }
}
