package org.example.ProyectoPOO.model.administracion;

import lombok.Getter;
import lombok.Setter;
import org.example.ProyectoPOO.model.BaseEntity;

import javax.persistence.Entity;

@Entity
@Getter
@Setter

public class Sucursal extends BaseEntity {

    private String nombre;

    private String direccion;

    private String Horario;

}
