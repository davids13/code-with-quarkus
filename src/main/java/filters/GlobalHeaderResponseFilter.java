package filters;

import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerResponseContext;
import jakarta.ws.rs.container.ContainerResponseFilter;
import jakarta.ws.rs.ext.Provider;
import org.jboss.logging.Logger;

import java.io.IOException;
import java.util.UUID;

/**
 * Add a custom header X-Global-Trace-ID to every response and measure how long the request took
 */
@Provider
public class GlobalHeaderResponseFilter implements ContainerResponseFilter {

    private static final Logger LOGGER = Logger.getLogger(GlobalHeaderResponseFilter.class.getName());

    @Override
    public void filter(ContainerRequestContext containerRequestContext, ContainerResponseContext containerResponseContext) throws IOException {
        containerResponseContext.getHeaders().add("X-Global-Trace-ID", UUID.randomUUID().toString().substring(0, 8));

        Object startTimeObject = containerRequestContext.getProperty("request-start-time");

        if (startTimeObject instanceof Long) {
            long durationNanos = System.nanoTime() - (Long) startTimeObject;
            LOGGER.infof("[GLOBAL] Request processed in %.2f mf", durationNanos / 1_000_000.0);
        }
        LOGGER.info("[GLOBAL] Response filter finished.");
    }
}
