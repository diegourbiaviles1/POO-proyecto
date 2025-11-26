package org.example.ProyectoPOO.model.administracion;

import lombok.Getter;
import lombok.Setter;
import org.example.ProyectoPOO.model.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
@Getter @Setter
public class Cliente extends BaseEntity {

    @Column(length = 100)
    private String nombreCompleto;

    @Column(length = 20)
    private String cedula;

    @Column(length = 30)
    private String telefono;

    @Column(length = 80)
    private String email;

    @Column(length = 150)
    private String direccion;
}
