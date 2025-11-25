package org.proyecto.gestioninventario.model;

import lombok.Getter;
import lombok.Setter;
import org.example.ProyectoPOO.model.BaseEntity;
import org.example.ProyectoPOO.model.administracion.Sucursal;
import org.example.ProyectoPOO.model.bodega.Paquete;
import org.example.ProyectoPOO.model.transporte.EnvioEstado;
import org.openxava.annotations.*;
import org.openxava.calculators.CurrentLocalDateCalculator;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Collection;

@Entity
@Getter
@Setter
public class Embarque extends BaseEntity {

    @Id
    private Long id;
    @Column(length = 20)
    private String numeroVueloContenedor; // Ej: VUELO-505

    @DefaultValueCalculator(CurrentLocalDateCalculator.class)
    private LocalDate fechaSalida;

    @ManyToOne(fetch = FetchType.LAZY)
    @DescriptionsList
    private Sucursal origen;

    @ManyToOne(fetch = FetchType.LAZY)
    @DescriptionsList
    private Sucursal destino;

    @Enumerated(EnumType.STRING)
    private EnvioEstado estado;


    @OneToMany(mappedBy = "embarque")
    @ListProperties("trackingProveedor" + "descripcion" + "cliente.nombreCompleto" + "pesoLibras" + "costoEnvio")
    private Collection<Paquete> paquetes;
}
