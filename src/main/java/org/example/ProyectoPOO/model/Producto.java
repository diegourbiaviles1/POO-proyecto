package org.example.ProyectoPOO.model;

import org.openxava.annotations.DescriptionsList;
import org.openxava.annotations.TextArea;

import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

public class Producto extends BaseEntity {

    private String nombre;

    @TextArea
    private String descripcion;

    @ManyToOne(fetch = FetchType.LAZY)
    @DescriptionsList
    private Categoria categoria;
}
