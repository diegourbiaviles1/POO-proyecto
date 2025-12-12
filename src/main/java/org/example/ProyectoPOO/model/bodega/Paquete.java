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

@Entity
@Getter
@Setter
@Views({
        // Vista principal del módulo Paquete
        @View(
                members =
                        "id, trackingProveedor, descripcion;" +
                                "cliente, proveedor;" +
                                "ubicacionActual, ubicacionDetalle;" +
                                "tipoEnvio, pesoLibras, costoEnvio;" +
                                "estadoActual, embarque, facturado"
        ),
        // Vista usada por la acción Change location
        @View(
                name = "cambiarUbicacion",
                members = "id; ubicacionActual, ubicacionDetalle"
        )
})
public class Paquete extends BaseEntity implements Trackeable {

    // Código de tracking que da el proveedor (Amazon, eBay, etc.)
    @Column(length = 50, unique = true)
    private String trackingProveedor;

    @Column(length = 150)
    private String descripcion;

    @ManyToOne(optional = false)
    private Cliente cliente;

    @ManyToOne
    private Proveedor proveedor;

    // Sucursal donde está físicamente el paquete
    @ManyToOne
    private Sucursal ubicacionActual;

    // Detalle de ubicación dentro de la sucursal/bodega
    @ManyToOne
    @Required
    @DescriptionsList(descriptionProperties = "sucursal.nombre")
    private UbicacionAlmacen ubicacionDetalle;

    @Enumerated(EnumType.STRING)
    private TipoEnvio tipoEnvio;          // AEREO / MARITIMO, etc.

    private BigDecimal pesoLibras;

    private BigDecimal costoEnvio;

    @Enumerated(EnumType.STRING)
    private EstadoEnvio estadoActual;

    @ManyToOne
    private Embarque embarque;

    // Indica si ya se facturó este paquete
    private boolean facturado;

    // Historial de movimientos para el tracking
    @OneToMany(mappedBy = "paquete", cascade = CascadeType.ALL)
    private List<Movimiento> historial;

    @Override
    public List<Movimiento> getHistorial() {
        return historial;
    }

    /**
     * Al guardar por primera vez el paquete,
     * si no se indicó un estado lo ponemos por defecto.
     */
    @PrePersist
    public void onPrePersist() {
        if (estadoActual == null) {
            estadoActual = EstadoEnvio.EN_BODEGA_USA; // ajusta al valor que tengas en tu enum
        }
    }

    /**
     * Sincroniza ubicacionActual con la sucursal de la ubicacionDetalle.
     */
    public void setUbicacionDetalle(UbicacionAlmacen ubicacionDetalle) {
        this.ubicacionDetalle = ubicacionDetalle;
        if (ubicacionDetalle != null) {
            this.ubicacionActual = ubicacionDetalle.getSucursal();
        } else {
            this.ubicacionActual = null;
        }
    }
}
