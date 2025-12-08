package org.example.ProyectoPOO.model.actions;

import org.example.ProyectoPOO.model.administracion.Usuario;
import org.example.ProyectoPOO.multitenancy.TenantContext;
import com.openxava.naviox.actions.SignInAction;
import org.openxava.jpa.XPersistence;
import org.openxava.util.Users;

import javax.persistence.NoResultException;

public class CustomSignInAction extends SignInAction {   //

    @Override
    public void execute() throws Exception {

        super.execute();

        if (getErrors().contains()) {
            System.out.println("Login falló, no se setea TenantContext");
            return;
        }

        String username = Users.getCurrent();
        System.out.println("Usuario autenticado: " + username);

        try {
            Usuario usuario = XPersistence.getManager()
                    .createQuery("from Usuario u where u.username = :username", Usuario.class)
                    .setParameter("username", username)
                    .getSingleResult();

            Long sucursalId = usuario.getSucursal().getId();
            System.out.println("Sucursal del usuario: " + sucursalId);

            TenantContext.setCurrentTenant(String.valueOf(sucursalId));
            System.out.println("TenantContext seteado a: " + TenantContext.getCurrentTenant());

        } catch (NoResultException ex) {
            System.out.println("No se encontró Usuario en BD para username=" + username);
        }
    }
}
