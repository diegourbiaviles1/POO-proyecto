package org.example.ProyectoPOO.model;

import org.example.ProyectoPOO.model.bodega.Movimiento;

import java.util.List;

public interface Trackeable {
    List<Movimiento> getHistorial();
}
