package org.example.ProyectoPOO.model.bodega;

import lombok.Getter;
import lombok.Setter;
import org.example.ProyectoPOO.model.BaseEntity;
import org.example.ProyectoPOO.model.administracion.Sucursal;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter @Setter
public class Embarque extends BaseEntity {

    private String numeroVueloContenedor;
    private LocalDateTime fechaSalida;

    @ManyToOne(optional = false)
    private Sucursal origen;

    @ManyToOne(optional = false)
    private Sucursal destino;

    @ManyToOne(optional = false)
    private Transportista transportista;

    @OneToMany(mappedBy = "embarque")
    private List<Paquete> paquetes;
}
