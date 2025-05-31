package persistence;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import org.jboss.logging.Logger;

@ApplicationScoped
public class AuditService {

    public static final Logger LOG = Logger.getLogger(AuditService.class);

    @Transactional(Transactional.TxType.REQUIRES_NEW)
    public void logAuditEvent(final String event) {
        LOG.info("AUDIT LOG (New Transaction): " + event);
    }

    @Transactional(Transactional.TxType.REQUIRED)
    public void logAuditEventInSameTransaction(final String event) {
        LOG.info("AUDIT LOG (Same Transaction): " + event);
    }

}
