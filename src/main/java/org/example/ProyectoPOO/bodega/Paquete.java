package org.example.ProyectoPOO.bodega;

import lombok.Getter;
import lombok.Setter;
import org.example.ProyectoPOO.administracion.Sucursal;
import org.example.ProyectoPOO.model.BaseEntity;
import org.example.ProyectoPOO.model.Categoria;
import org.example.ProyectoPOO.transporte.EnvioEstado;
import org.openxava.annotations.*;

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


}
