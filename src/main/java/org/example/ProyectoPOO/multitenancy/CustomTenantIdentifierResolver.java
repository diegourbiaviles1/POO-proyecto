package org.example.ProyectoPOO.multitenancy;

import org.hibernate.context.spi.CurrentTenantIdentifierResolver;

public class CustomTenantIdentifierResolver implements CurrentTenantIdentifierResolver {

    @Override
    public String resolveCurrentTenantIdentifier() {
        return TenantContext.getCurrentTenant();
    }

    @Override
    public boolean validateExistingCurrentSessions() {
        return true;
    }
}

