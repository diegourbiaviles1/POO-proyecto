package org.example.ProyectoPOO.multitenancy;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

import org.example.ProyectoPOO.model.administracion.Usuario;
import org.openxava.filters.IFilter;
import org.openxava.filters.FilterException;
import org.openxava.jpa.XPersistence;
import org.openxava.util.Users;

public class SucursalActualFilter implements IFilter {

    @Override
    public Object filter(Object o) throws FilterException {
        try {
            String username = Users.getCurrent();   // usuario logueado

            if (username == null) {
                // Sin usuario: no filtrar nada (o devolver algo neutro)
                return new Object[] { null };
            }

            // Busca el Usuario por username
            EntityManager em = XPersistence.getManager();

            Usuario usuario = em.createQuery(
                            "from Usuario u where u.username = :username",
                            Usuario.class
                    )
                    .setParameter("username", username)
                    .getSingleResult();

            Long sucursalId = usuario.getSucursal().getId();

            // IMPORTANTE: devolver SIEMPRE Object[]
            return new Object[] { sucursalId };

        } catch (NoResultException e) {
            // Usuario sin sucursal o no encontrado → no aplica filtro
            return new Object[] { null };
        } catch (Exception e) {
            throw new FilterException("Error en SucursalActualFilter", e);
        }
    }
}
