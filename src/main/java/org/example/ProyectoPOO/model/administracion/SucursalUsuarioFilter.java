package org.example.ProyectoPOO.model.administracion;

import org.example.ProyectoPOO.model.administracion.Usuario;
import org.openxava.filters.FilterException;
import org.openxava.filters.IFilter;
import org.openxava.jpa.XPersistence;
import org.openxava.util.Is;
import org.openxava.util.Users;

import javax.persistence.NoResultException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SucursalUsuarioFilter implements IFilter {

    @Override
    public Object filter(Object o) throws FilterException {

        Long sucursalId = null;

        try {
            sucursalId = getSucursalIdDeUsuarioActual();
            System.out.println("[SucursalUsuarioFilter] sucursalId para usuario actual = " + sucursalId);
        }
        catch (Exception ex) {
            // IMPORTANTE: no romper la aplicación, solo loguear y devolver los parámetros originales
            System.out.println("[SucursalUsuarioFilter] ERROR obteniendo sucursal del usuario: " + ex.getMessage());
            ex.printStackTrace();
            // Dejamos la consulta sin filtro adicional
            if (o == null) return new Object[]{};
            return o;
        }

        // Si no pudimos obtener sucursal, devolvemos los parámetros originales
        if (sucursalId == null) {
            if (o == null) return new Object[]{};
            return o;
        }

        // A partir de aquí sí aplicamos el filtro por sucursal

        if (o == null) {
            // solo nuestro parámetro
            return new Object[]{ sucursalId };
        }

        if (o instanceof Object[]) {
            List<Object> params = new ArrayList<>(Arrays.asList((Object[]) o));
            // nuestro parámetro va primero porque en baseCondition hay un único "?"
            params.add(0, sucursalId);
            return params.toArray();
        }

        // Un solo parámetro, lo convertimos a array y le metemos delante la sucursal
        return new Object[]{ sucursalId, o };
    }

    private Long getSucursalIdDeUsuarioActual() throws Exception {
        String username = Users.getCurrent();
        System.out.println("[SucursalUsuarioFilter] Users.getCurrent() = " + username);

        if (Is.emptyString(username)) {
            throw new Exception("No hay usuario autenticado");
        }

        Usuario usuario;
        try {
            usuario = XPersistence.getManager()
                    .createQuery("from Usuario u where u.username = :username", Usuario.class)
                    .setParameter("username", username)
                    .getSingleResult();
        }
        catch (NoResultException ex) {
            throw new Exception("Usuario no encontrado en tabla Usuario: " + username);
        }

        if (usuario.getSucursal() == null) {
            throw new Exception("El usuario " + username + " no tiene sucursal asignada");
        }

        return usuario.getSucursal().getId();
    }
}
