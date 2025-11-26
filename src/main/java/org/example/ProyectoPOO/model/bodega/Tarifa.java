package org.example.ProyectoPOO.model.bodega;

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
public class Tarifa extends BaseEntity {

    @Enumerated(EnumType.STRING)
    private TipoEnvio tipoEnvio;

    @ManyToOne(optional = false)
    private Categoria categoria;

    private LocalDate fechaVigenciaInicio;
    private LocalDate fechaVigenciaFin;

    private BigDecimal precioPorLibra;

    public boolean esVigenteEn(LocalDate fecha) {
        return (fecha.isEqual(fechaVigenciaInicio) || fecha.isAfter(fechaVigenciaInicio))
                && (fecha.isEqual(fechaVigenciaFin) || fecha.isBefore(fechaVigenciaFin));
    }
}
