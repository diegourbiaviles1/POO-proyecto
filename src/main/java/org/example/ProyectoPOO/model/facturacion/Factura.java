package org.example.ProyectoPOO.model.facturacion;

import lombok.Getter;
import lombok.Setter;
import org.example.ProyectoPOO.model.BaseEntity;
import org.example.ProyectoPOO.model.Facturable;
import org.example.ProyectoPOO.model.administracion.Cliente;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Entity
@Getter @Setter
public class Factura extends BaseEntity implements Facturable {

    private int anio;      // por ejemplo 2025
    private int numero;    // correlativo dentro del año

    private LocalDate fecha;

    @ManyToOne(optional = false)
    private Cliente cliente;

    @OneToMany(mappedBy = "factura", cascade = CascadeType.ALL)
    private List<DetalleFactura> detalles;

    @OneToMany(mappedBy = "factura", cascade = CascadeType.ALL)
    private List<PagoFactura> pagos;

    private boolean pagada;

    @PrePersist
    public void onPrePersist() {
        if (fecha == null) {
            fecha = LocalDate.now();
        }
        if (anio == 0) {
            anio = fecha.getYear();
        }
    }

    // Implementación de Facturable
    @Override
    public Cliente getCliente() {
        return cliente;
    }

    public BigDecimal getSubtotal() {
        if (detalles == null || detalles.isEmpty()) {
            return BigDecimal.ZERO;
        }
        return detalles.stream()
                .map(DetalleFactura::getImporte)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    @Override
    public BigDecimal getTotal() {
        // Si luego quieres impuestos o descuentos, lo sumas aquí
        return getSubtotal();
    }

    public BigDecimal getSaldoPendiente() {
        BigDecimal pagado = (pagos == null || pagos.isEmpty())
                ? BigDecimal.ZERO
                : pagos.stream()
                .map(PagoFactura::getMonto)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        return getTotal().subtract(pagado);
    }
}
