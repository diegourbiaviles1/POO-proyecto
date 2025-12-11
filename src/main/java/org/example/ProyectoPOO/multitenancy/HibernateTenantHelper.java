package org.example.ProyectoPOO.multitenancy; // <-- ajusta si es distinto

import org.hibernate.Session;
import org.openxava.jpa.XPersistence;

import java.util.Set;

public class HibernateTenantHelper {

    public static void applySucursalFilter() {
        // Solo mostramos los filtros definidos y NO activamos ninguno
        Session session = (Session) XPersistence.getManager().getDelegate();
        Set<String> filtros = session.getSessionFactory().getDefinedFilterNames();
        System.out.println("Filtros definidos en SessionFactory: " + filtros);

        // IMPORTANTE: ya no intentamos habilitar "sucursalFilter"
        // porque ahora usamos el filtro de OpenXava (@Tab + SucursalActualFilter)
    }

    public static void disableSucursalFilter() {
        // Metodo vacío para que si lo llamas en TenantFilter no pase nada
    }
}
