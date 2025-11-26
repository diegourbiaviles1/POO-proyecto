package org.example.ProyectoPOO.model.bodega;

import lombok.Getter;
import lombok.Setter;
import org.example.ProyectoPOO.model.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
@Getter @Setter
public class Producto extends BaseEntity {

    @Column(length = 100)
    private String descripcion;

    @ManyToOne(optional = false)
    private Categoria categoria;
}
