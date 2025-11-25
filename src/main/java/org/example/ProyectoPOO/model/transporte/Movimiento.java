package org.example.ProyectoPOO.model.transporte;

import lombok.Getter;
import lombok.Setter;
import org.openxava.annotations.ReadOnly;

import javax.persistence.Embeddable;
import java.time.LocalDateTime;

@Embeddable
@Getter
@Setter

public class Movimiento {
    @ReadOnly
    private LocalDateTime timestamp;

    private EnvioEstado estado;

    private String descripcion;
}
