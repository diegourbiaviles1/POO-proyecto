package org.example.ProyectoPOO.model.bodega;

import lombok.Getter;
import lombok.Setter;
import org.example.ProyectoPOO.model.BaseEntity;
import org.example.ProyectoPOO.model.administracion.Sucursal;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
@Getter @Setter
public class InventarioSucursal extends BaseEntity {

    @ManyToOne(optional = false)
    private Sucursal sucursal;

    @ManyToOne(optional = false)
    private Categoria categoria;

    private int cantidad; // paquetes / productos presentes
}
