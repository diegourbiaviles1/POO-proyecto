package org.example.ProyectoPOO.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;

@Entity
@Getter
@Setter

public class Sucursal extends BaseEntity{

    private String nombre;

    private String direccion;

    private String Horario;

}
