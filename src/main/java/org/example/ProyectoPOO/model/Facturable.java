package org.example.ProyectoPOO.model;

import org.example.ProyectoPOO.model.administracion.Cliente;

import java.math.BigDecimal;

public interface Facturable {
    Cliente getCliente();
    BigDecimal getTotal();
}
