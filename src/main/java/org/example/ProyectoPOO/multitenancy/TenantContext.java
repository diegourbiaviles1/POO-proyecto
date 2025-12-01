package org.example.ProyectoPOO.multitenancy;

public class TenantContext {

    private static final ThreadLocal<String> CURRENT_TENANT = new ThreadLocal<>();

    public static String getCurrentTenant() {
        String tenant = CURRENT_TENANT.get();
        return tenant != null ? tenant : "default";
    }

    public static void setCurrentTenant(String tenantId) {
        CURRENT_TENANT.set(tenantId);
    }

    public static void clear() {
        CURRENT_TENANT.remove();
    }
}