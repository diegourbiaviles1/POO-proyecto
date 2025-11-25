package org.example.ProyectoPOO.model.bodega;

import lombok.Getter;
import lombok.Setter;
import org.example.ProyectoPOO.model.administracion.Sucursal;
import org.example.ProyectoPOO.model.BaseEntity;
import org.example.ProyectoPOO.model.transporte.EnvioEstado;
import org.openxava.annotations.*;
import  org.proyecto.gestioninventario.model.Embarque;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Getter
@Setter

public class Paquete extends BaseEntity {

    @Column(length=28)
    private String trackingProveedor;

    @TextArea
    private String descripcion;

    @ManyToOne(fetch = FetchType.LAZY)
    @DescriptionsList
    private Sucursal ubicacionActual;

    @Enumerated(EnumType.STRING)
    @Required
    private TipoEnvio tipoEnvio;

    private BigDecimal pesoLibras;

    @Money
    @ReadOnly
    @Calculation("pesoLibras * tipoEnvio")
    private BigDecimal costoEnvio;

    @Enumerated(EnumType.STRING)
    private EnvioEstado estado;

    @ManyToOne(fetch = FetchType.LAZY)
    private Embarque embarque;


}
