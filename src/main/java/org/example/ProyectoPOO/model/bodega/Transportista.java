package org.example.ProyectoPOO.model.bodega;

import lombok.Getter;
import lombok.Setter;
import org.example.ProyectoPOO.model.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
@Getter @Setter
public class Transportista extends BaseEntity {

    @Column(length = 80)
    private String nombre;

    @Column(length = 20)
    private String codigoIata;

    @Column(length = 80)
    private String contacto;
}
