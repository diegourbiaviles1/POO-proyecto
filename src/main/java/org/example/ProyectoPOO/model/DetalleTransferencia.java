package org.example.ProyectoPOO.model;

import lombok.Getter;
import lombok.Setter;
import org.example.ProyectoPOO.bodega.Paquete;

import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

@Embeddable
@Getter
@Setter


public class DetalleTransferencia {

    @ManyToOne(fetch = FetchType.LAZY)
    private Paquete producto;
    private int cantidad;
}
