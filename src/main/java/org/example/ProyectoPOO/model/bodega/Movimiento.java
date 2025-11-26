package org.example.ProyectoPOO.model.bodega;

import lombok.Getter;
import lombok.Setter;
import org.example.ProyectoPOO.model.BaseEntity;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter @Setter
public class Movimiento extends BaseEntity {

    private LocalDateTime timestamp;

    @Enumerated(EnumType.STRING)
    private EstadoEnvio estado;

    @Column(length = 150)
    private String descripcion;

    @ManyToOne(optional = false)
    private Paquete paquete;
}
