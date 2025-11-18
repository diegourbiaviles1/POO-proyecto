package org.example.ProyectoPOO.model;


import lombok.Getter;
import lombok.Setter;
import org.dom4j.tree.AbstractEntity;
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
    private Producto producto;

    private int cantidad;
}
