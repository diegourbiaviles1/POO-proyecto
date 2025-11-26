package org.example.ProyectoPOO.model.facturacion;

import lombok.Getter;
import lombok.Setter;
import org.example.ProyectoPOO.model.BaseEntity;
import org.example.ProyectoPOO.model.bodega.Paquete;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import java.math.BigDecimal;

@Entity
@Getter @Setter
public class DetalleFactura extends BaseEntity {

    @ManyToOne(optional = false)
    private Factura factura;

    @ManyToOne(optional = false)
    private Paquete paquete;

    private int cantidad;
    private BigDecimal precio;

    public BigDecimal getImporte() {
        return precio.multiply(BigDecimal.valueOf(cantidad));
    }
}
