package tenant;

import io.quarkus.hibernate.orm.runtime.tenant.TenantResolver;
import io.quarkus.security.identity.SecurityIdentity;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;

@RequestScoped
public class MyTenantResolver implements TenantResolver {

    SecurityIdentity securityIdentity;

    @Inject
    public MyTenantResolver(SecurityIdentity securityIdentity) {
        this.securityIdentity = securityIdentity;
    }

    @Override
    public String getDefaultTenantId() {
        return "UNKNOWN_TENANT";
    }

    @Override
    public String resolveTenantId() {
        if (securityIdentity != null && !securityIdentity.isAnonymous()) {
            return securityIdentity.getPrincipal().getName();
        }
        return getDefaultTenantId();
    }
}
