package org.example.ProyectoPOO.multitenancy;

import org.example.ProyectoPOO.model.administracion.Usuario;
import org.hibernate.Session;
import org.openxava.jpa.XPersistence;
import org.openxava.util.Users;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

public class HibernateTenantHelper {

    public static void applySucursalFilter() {

        String username = Users.getCurrent();
        if (username == null || username.trim().isEmpty()) {
            System.out.println("applySucursalFilter(): sin usuario autenticado, no se habilita filtro");
            return;
        }

        EntityManager em = XPersistence.getManager();
        Session session = em.unwrap(Session.class);

        try {
            Usuario usuario = em.createQuery(
                            "from Usuario u where u.username = :username",
                            Usuario.class
                    )
                    .setParameter("username", username)
                    .getSingleResult();

            Long sucursalId = usuario.getSucursal().getId();

            System.out.println("applySucursalFilter(): usuario=" + username +
                    ", sucursalId=" + sucursalId);

            session.enableFilter("sucursalFilter")
                    .setParameter("sucursalId", sucursalId);

            TenantContext.setCurrentTenant(String.valueOf(sucursalId));

        } catch (NoResultException ex) {
            System.out.println("applySucursalFilter(): no se encontró Usuario en BD para username=" + username);
        }
    }
}
