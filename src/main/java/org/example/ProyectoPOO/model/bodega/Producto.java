package org.example.ProyectoPOO.model.bodega;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;

import org.example.ProyectoPOO.model.BaseEntity;
import org.example.ProyectoPOO.model.administracion.Sucursal;
import org.example.ProyectoPOO.model.administracion.Usuario;
import org.example.ProyectoPOO.multitenancy.SucursalActualFilter;
import org.openxava.annotations.DescriptionsList;
import org.openxava.annotations.Required;
import org.openxava.annotations.Tab;
import org.openxava.annotations.View;
import org.openxava.jpa.XPersistence;
import org.openxava.util.Users;

@Entity
@View(members =
        "nombre, descripcion, categoria, sucursal"
)
@Tab(
        baseCondition = "${sucursal.id} = ?",
        filter = SucursalActualFilter.class,
        properties = "id, nombre, descripcion, categoria.nombre, sucursal.nombre"
)
public class Producto extends BaseEntity {

    @Column(length = 100)
    private String nombre;

    @Column(length = 255)
    private String descripcion;

    @ManyToOne(optional = true)
    @Required
    @DescriptionsList
    private Sucursal sucursal;

    @ManyToOne(optional = false)
    @Required
    @DescriptionsList(descriptionProperties = "nombre") // o la propiedad que quieras mostrar
    private Categoria categoria;

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    // Getters y setters
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public Sucursal getSucursal() { return sucursal; }
    public void setSucursal(Sucursal sucursal) { this.sucursal = sucursal; }

    /**
     * OJO: además de este @PrePersist se ejecuta también el @PrePersist
     * de BaseEntity, que rellena tenantId y fechaCreacion.
     */
    @PrePersist
    private void asignarSucursalPorDefecto() {
        // Si ya se eligió sucursal en la UI, no tocamos nada
        if (this.sucursal != null) return;

        String username = Users.getCurrent();
        if (username == null || username.trim().isEmpty()) return;

        try {
            Usuario u = (Usuario) XPersistence.getManager()
                    .createQuery("select u from Usuario u where u.username = :username")
                    .setParameter("username", username)
                    .getSingleResult();

            if (u != null && u.getSucursal() != null) {
                this.sucursal = u.getSucursal();
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
