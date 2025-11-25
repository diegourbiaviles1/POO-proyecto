package org.example.ProyectoPOO.transporte;

import lombok.Getter;
import lombok.Setter;
import org.example.ProyectoPOO.administracion.Sucursal;
import org.example.ProyectoPOO.model.BaseEntity;
import org.example.ProyectoPOO.model.DetalleTransferencia;
import org.openxava.annotations.DefaultValueCalculator;
import org.openxava.annotations.DescriptionsList;
import org.openxava.annotations.ListProperties;
import org.openxava.annotations.ReadOnly;
import org.openxava.calculators.CurrentLocalDateCalculator;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Collection;

@Entity
@Getter
@Setter

public class Transferencia extends BaseEntity {

    @Column(length = 20)
    @ReadOnly
    private String numeroSeguimiento;

    @DefaultValueCalculator(CurrentLocalDateCalculator.class)
    @ReadOnly
    private LocalDate fecha;

    @ManyToOne(fetch = FetchType.LAZY)
    @DescriptionsList(descriptionProperties = "nombre")
    private Sucursal origen;

    @ManyToOne(fetch = FetchType.LAZY)
    @DescriptionsList(descriptionProperties = "nombre")
    private Sucursal destino;

    @Enumerated(EnumType.STRING)
    private EnvioEstado estado;

    @ElementCollection
    @ListProperties("producto.nombre, cantidad")
    private Collection<DetalleTransferencia> detalles;

    @ElementCollection
    @ReadOnly
    @ListProperties("timestamp, estado, descripcion")
    private Collection<Movimiento> historial;
}
