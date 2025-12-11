package org.example.ProyectoPOO.model.actions.paquete;

import org.openxava.actions.ViewBaseAction;
import org.openxava.model.MapFacade;

import java.util.HashMap;
import java.util.Map;

/**
 * Guarda la nueva ubicacionActual (y opcionalmente ubicacionDetalle) del paquete.
 */
public class GuardarUbicacionPaqueteAction extends ViewBaseAction {

    @Override
    public void execute() throws Exception {

        Map<String, Object> valoresVista = getView().getValues();
        Map<String, Object> key         = getView().getKeyValues();

        Map<String, Object> cambios = new HashMap<>();

        cambios.put("ubicacionActual", valoresVista.get("ubicacionActual"));


        MapFacade.setValues(
                "Paquete",   // nombre del modelo
                key,         // { id = ... }
                cambios      // { ubicacionActual = ..., [ubicacionDetalle = ...] }
        );

        closeDialog();

    }
}
