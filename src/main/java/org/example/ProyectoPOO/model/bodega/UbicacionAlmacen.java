package org.example.ProyectoPOO.model.bodega;

import lombok.Getter;
import lombok.Setter;
import org.example.ProyectoPOO.model.BaseEntity;
import org.example.ProyectoPOO.model.administracion.Sucursal;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
@Getter @Setter
public class UbicacionAlmacen extends BaseEntity {

    private String pasillo;
    private String estante;
    private String nivel;

    @ManyToOne(optional = false)
    private Sucursal sucursal;
}
