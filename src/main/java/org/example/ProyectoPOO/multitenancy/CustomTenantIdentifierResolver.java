package org.example.ProyectoPOO.multitenancy;

import org.hibernate.context.spi.CurrentTenantIdentifierResolver;

public class CustomTenantIdentifierResolver implements CurrentTenantIdentifierResolver {

    @Override
    public String resolveCurrentTenantIdentifier() {
        String tenantId = TenantContext.getCurrentTenant();
        // Si es null (pantalla de login), usa el esquema por defecto que creaste en el paso 1
        if (tenantId == null) {
            return "SucursalCentral";
        }
        return tenantId;
    }

    @Override
    public boolean validateExistingCurrentSessions() {
        return true;
    }
}

