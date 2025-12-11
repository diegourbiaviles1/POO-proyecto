package org.example.ProyectoPOO.model.administracion;

import lombok.Getter;
import lombok.Setter;
import org.example.ProyectoPOO.model.BaseEntity;
import org.example.ProyectoPOO.model.administracion.Sucursal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
@Getter @Setter
public class Usuario extends BaseEntity {

    @Column(length = 40, unique = true)
    private String username;

    private String passwordHash;

    @ManyToOne(optional = false)
    private Rol rol;

    public Sucursal getSucursal() {
        return sucursal;
    }

    public void setSucursal(Sucursal sucursal) {
        this.sucursal = sucursal;
    }

    @ManyToOne(optional = false)
    private Sucursal sucursal;
}
