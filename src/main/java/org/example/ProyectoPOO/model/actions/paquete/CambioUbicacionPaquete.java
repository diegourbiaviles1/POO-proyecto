package org.example.ProyectoPOO.model.actions.paquete;

import lombok.Getter;
import lombok.Setter;
import org.example.ProyectoPOO.model.administracion.Sucursal;
import org.openxava.annotations.DescriptionsList;

@Getter
@Setter
public class CambioUbicacionPaquete {

    // MUY IMPORTANTE: el nombre debe ser IGUAL
    // al campo de Paquete que vamos a cambiar
    @DescriptionsList
    private Sucursal ubicacionActual;
}
