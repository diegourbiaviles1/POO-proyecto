package org.example.ProyectoPOO.multitenancy;

public class TenantContext {

    private static final ThreadLocal<String> CURRENT_TENANT = new ThreadLocal<>();

    public static String getCurrentTenant() {
        String tenant = CURRENT_TENANT.get();
        // CAMBIO: Usar "dbo" en lugar de "default"
        return tenant != null ? tenant : "dbo";
    }

    public static void setCurrentTenant(String tenantId) {
        CURRENT_TENANT.set(tenantId);
    }

    public static void clear() {
        CURRENT_TENANT.remove();
    }
}