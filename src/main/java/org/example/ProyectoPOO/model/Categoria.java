package org.example.ProyectoPOO.model;

import lombok.Getter;
import lombok.Setter;
import org.openxava.annotations.TextArea;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
@Getter
@Setter



public class Categoria extends BaseEntity {

    private String nombre;
    @Column(length = 50)

    @TextArea
    private String descripcion;


}
