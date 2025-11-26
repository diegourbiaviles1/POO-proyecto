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

    private int anio;
    private int numero;

    private LocalDate fecha;

    @ManyToOne(optional = false)
    private Cliente cliente;

    @OneToMany(mappedBy = "factura", cascade = CascadeType.ALL)
    private List<DetalleFactura> detalles;

    @OneToMany(mappedBy = "factura", cascade = CascadeType.ALL)
    private List<PagoFactura> pagos;

    private boolean pagada;

    public BigDecimal getSubtotal() {
        return detalles.stream()
                .map(DetalleFactura::getImporte)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public BigDecimal getTotal() {
        // si hay impuestos, agrégalos aquí
        return getSubtotal();
    }

    public BigDecimal getSaldoPendiente() {
        BigDecimal pagado = pagos.stream()
                .map(PagoFactura::getMonto)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        return getTotal().subtract(pagado);
    }
}
