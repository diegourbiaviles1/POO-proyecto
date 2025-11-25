package org.example.ProyectoPOO.model;


import lombok.Getter;
import lombok.Setter;
import org.example.ProyectoPOO.administracion.Sucursal;
import org.example.ProyectoPOO.bodega.Paquete;
import org.openxava.annotations.DescriptionsList;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

@Entity
@Getter
@Setter
public class Stock extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @DescriptionsList(descriptionProperties = "nombre")
    private Sucursal sucursal;


    @ManyToOne(fetch = FetchType.LAZY)
    @DescriptionsList(descriptionProperties = "nombre")
    private Paquete paquete;

    private int cantidad;
}
