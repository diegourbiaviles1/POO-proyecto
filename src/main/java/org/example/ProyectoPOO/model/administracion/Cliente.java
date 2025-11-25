package org.example.ProyectoPOO.model.administracion;


import lombok.Getter;
import lombok.Setter;
import org.example.ProyectoPOO.model.BaseEntity;

import javax.persistence.Entity;

@Entity
@Getter
@Setter
public class Cliente extends BaseEntity {

    private String nombreCompleto;

    private  String cedula;

    private String telefono;

}
