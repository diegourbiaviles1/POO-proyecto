package org.example.ProyectoPOO.model.bodega;

import lombok.Getter;
import lombok.Setter;
import org.example.ProyectoPOO.annotations.FilterRestrictiva; // <--- NUEVA IMPORTACIÓN
import org.example.ProyectoPOO.model.BaseEntity;
import org.example.ProyectoPOO.model.administracion.Sucursal;
import org.openxava.annotations.Tab;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity

@FilterRestrictiva
@Tab(
        // Ya no necesitamos 'filter' ni 'baseCondition' aquí porque el Inspector intercepta el SQL globalmente
        properties = "nombre, descripcion, categoria.descripcion, sucursal.nombre"
)

@Getter @Setter
public class Producto extends BaseEntity {

    @Column(length = 100)
    private String nombre;

    @Column(length = 100)
    private String descripcion;

    @ManyToOne(optional = false)
    private Categoria categoria;

    @ManyToOne(optional = false)
    @JoinColumn(name = "sucursal_id") // El inspector buscará esta columna 'sucursal_id'
    private Sucursal sucursal;
}