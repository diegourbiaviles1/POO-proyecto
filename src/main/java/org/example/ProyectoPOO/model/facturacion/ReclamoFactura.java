package org.example.ProyectoPOO.model.facturacion;

import lombok.Getter;
import lombok.Setter;
import org.example.ProyectoPOO.model.BaseEntity;
import org.example.ProyectoPOO.model.administracion.Cliente;
import org.example.ProyectoPOO.model.bodega.Paquete;
import javax.persistence.PrePersist;


import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Getter @Setter
public class ReclamoFactura extends BaseEntity {

    private LocalDate fechaReclamo;

    @ManyToOne(optional = false)
    private Cliente cliente;

    @ManyToOne(optional = false)
    private Paquete paquete;

    @Column(length = 200)
    private String descripcionProblema;

    @Enumerated(EnumType.STRING)
    private EstadoReclamo estado;

    @Column(length = 200)
    private String resolucion;

    @PrePersist
    public void onPrePersist() {
        if (fechaReclamo == null) {
            fechaReclamo = LocalDate.now();
        }
        if (estado == null) {
            estado = EstadoReclamo.PENDIENTE;
        }
    }
}
