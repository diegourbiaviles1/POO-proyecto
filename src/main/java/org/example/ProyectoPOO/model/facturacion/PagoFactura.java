package org.example.ProyectoPOO.model.facturacion;

import lombok.Getter;
import lombok.Setter;
import org.example.ProyectoPOO.model.BaseEntity;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Getter @Setter
public class PagoFactura extends BaseEntity {

    private LocalDate fechaPago;

    @ManyToOne(optional = false)
    private Factura factura;

    private BigDecimal monto;

    @Enumerated(EnumType.STRING)
    private MetodoPago metodo;

    private String referenciaTransaccion;
}
