package org.example.ProyectoPOO.model.bodega;

import lombok.Getter;
import lombok.Setter;
import org.example.ProyectoPOO.model.BaseEntity;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import java.time.LocalDateTime;

@Entity
@Getter @Setter
public class Movimiento extends BaseEntity {

    private LocalDateTime timestamp;

    @Enumerated(EnumType.STRING)
    private EstadoEnvio estado;

    private String descripcion;

    @ManyToOne(optional = false)
    private Paquete paquete;

    @PrePersist
    public void onPrePersist() {
        if (timestamp == null) {
            timestamp = LocalDateTime.now();
        }
    }
}
