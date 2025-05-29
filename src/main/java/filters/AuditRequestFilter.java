package filters;

import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.ext.Provider;
import org.jboss.logging.Logger;

import java.io.IOException;

/**
 * Apply the annotations Audited only here
 */
@Provider
@Audited
public class AuditRequestFilter implements ContainerRequestFilter {

    public static final Logger LOG = Logger.getLogger(AuditRequestFilter.class);

    @Override
    public void filter(ContainerRequestContext containerRequestContext) throws IOException {
        LOG.infof("[AUDIT] Performing detailed audit check for: %s %s", containerRequestContext.getMethod(), containerRequestContext.getUriInfo().getPath());
    }
}
