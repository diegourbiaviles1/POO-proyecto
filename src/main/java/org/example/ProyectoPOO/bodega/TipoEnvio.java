package org.example.ProyectoPOO.bodega;

import lombok.Getter;

public enum TipoEnvio {
    AEREO(new java.math.BigDecimal("7.50")),
    MARITIMO(new java.math.BigDecimal("2.50"));

    @Getter
    private java.math.BigDecimal precioPorLibra;

    TipoEnvio(java.math.BigDecimal precioPorLibra) {
        this.precioPorLibra = precioPorLibra;
    }
}
