package org.example.ProyectoPOO.model.bodega;

import lombok.Getter;
import lombok.Setter;
import org.example.ProyectoPOO.model.BaseEntity;
import org.example.ProyectoPOO.model.administracion.Sucursal;
import org.hibernate.annotations.Filter;
import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.ParamDef;
import javax.persistence.JoinColumn;

import org.example.ProyectoPOO.model.administracion.SucursalUsuarioFilter;
import org.openxava.annotations.Tab;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
@FilterDef(
        name = "sucursalFilter",
        parameters = @ParamDef(name = "sucursalId", type = "long")
)
@Filter(name = "sucursalFilter", condition = "sucursal_id = :sucursalId")
@Getter @Setter

public class Producto extends BaseEntity {

    @Column(length = 100)
    private String nombre;

    @Column(length = 100)
    private String descripcion;

    @ManyToOne(optional = false)
    private Categoria categoria;

    @ManyToOne(optional = false)
    @JoinColumn(name = "sucursal_id")
    private Sucursal sucursal;
}
