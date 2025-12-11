package org.example.ProyectoPOO.model.bodega;

import lombok.Getter;
import lombok.Setter;
import org.example.ProyectoPOO.model.BaseEntity;
import org.example.ProyectoPOO.model.Trackeable;
import org.example.ProyectoPOO.model.administracion.Cliente;
import org.example.ProyectoPOO.model.administracion.Proveedor;
import org.example.ProyectoPOO.model.administracion.Sucursal;
import org.openxava.annotations.DescriptionsList;
import org.openxava.annotations.Required;
import org.openxava.annotations.View;
import org.openxava.annotations.Views;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Views({
        @View(members = "id, trackingProveedor, cliente, proveedor, ubicacionActual, ubicacionDetalle, ..."), // tu vista normal
        @View(
                name = "cambiarUbicacion",
                members = "id; ubicacionActual, ubicacionDetalle"   // aquí lo que quieres ver en el diálogo
        )
})
@Entity
@Getter @Setter
public class Paquete extends BaseEntity implements Trackeable {

    @Column(length = 50, unique = true)
    private String trackingProveedor; // código de Amazon, etc.

    @Column(length = 150)
    private String descripcion;

    @ManyToOne(optional = false)
    private Cliente cliente;

    @ManyToOne
    private Proveedor proveedor;

    // sucursal donde está físicamente
    @ManyToOne
    private Sucursal ubicacionActual;

    // detalle de ubicación dentro de la bodega/sucursal
    @ManyToOne
    @Required
    @DescriptionsList(descriptionProperties = "sucursal.nombre")
    private UbicacionAlmacen ubicacionDetalle;

    @Enumerated(EnumType.STRING)
    private TipoEnvio tipoEnvio;

    private BigDecimal pesoLibras;
    private BigDecimal costoEnvio;

    @Enumerated(EnumType.STRING)
    private EstadoEnvio estadoActual;

    @ManyToOne
    private Embarque embarque;

    private boolean facturado;

    @OneToMany(mappedBy = "paquete", cascade = CascadeType.ALL)
    private List<Movimiento> historial;

    @Override
    public List<Movimiento> getHistorial() {
        return historial;
    }

    @PrePersist
    public void onPrePersist() {
        // si no se indica estado, asumimos que está en bodega USA
        if (estadoActual == null) {
            estadoActual = EstadoEnvio.EN_BODEGA_USA;
        }
        // facturado ya es false por defecto, no hace falta tocarlo
    }

    public void setUbicacionDetalle(UbicacionAlmacen ubicacionDetalle) {
        this.ubicacionDetalle = ubicacionDetalle;

        if (ubicacionDetalle != null) {
            this.ubicacionActual = ubicacionDetalle.getSucursal();
        } else {
            this.ubicacionActual = null;
        }
    }


}
