package org.example.ProyectoPOO.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

@Embeddable
@Getter
@Setter


public class DetalleTransferencia {

    @ManyToOne(fetch = FetchType.LAZY)
    private Producto producto;
    private int cantidad;
}
