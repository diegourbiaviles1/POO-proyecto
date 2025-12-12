package org.example.ProyectoPOO.model.actions.paquete;

import org.openxava.actions.ViewBaseAction;
import org.openxava.model.MapFacade;

import java.util.HashMap;
import java.util.Map;

/**
 * Guarda la nueva ubicacionDetalle del paquete.
 * El setter de Paquete se encarga de actualizar ubicacionActual.
 */
public class GuardarUbicacionPaqueteAction extends ViewBaseAction {

    @Override
    public void execute() throws Exception {

        // Valores actuales de la vista del diálogo
        Map<String, Object> valoresVista = getView().getValues();

        // Clave primaria del paquete
        Map<String, Object> key = new HashMap<>();
        key.put("id", valoresVista.get("id"));

        // Cambios a aplicar
        Map<String, Object> cambios = new HashMap<>();
        // Cambiamos solo ubicacionDetalle; en la entidad Paquete
        // el método setUbicacionDetalle() actualizará ubicacionActual
        cambios.put("ubicacionDetalle", valoresVista.get("ubicacionDetalle"));

        // Guardar en BD
        MapFacade.setValues("Paquete", key, cambios);

        // Cerrar el diálogo
        closeDialog();
    }
}
