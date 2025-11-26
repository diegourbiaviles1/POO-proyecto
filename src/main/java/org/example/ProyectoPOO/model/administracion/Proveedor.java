package org.example.ProyectoPOO.model.administracion;

import lombok.Getter;
import lombok.Setter;
import org.example.ProyectoPOO.model.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
@Getter @Setter
public class Proveedor extends BaseEntity {

    @Column(length = 80)
    private String nombre; // Amazon, Ebay, etc.

    @Column(length = 200)
    private String url;
}
