package org.example.ProyectoPOO.model.actions.paquete;

import org.openxava.actions.TabBaseAction;

import java.util.Map;

public class CambiarUbicacionPaqueteAction extends TabBaseAction {

    @Override
    public void execute() throws Exception {

        Map[] seleccionados = getSelectedKeys();
        if (seleccionados == null || seleccionados.length == 0) {
            addError("Debe seleccionar al menos un registro");
            return;
        }

        Map keyValues = seleccionados[0];

        // Mostrar primero el diálogo
        showDialog();

        // Configurar vista del diálogo
        getView().setModelName("Paquete");
        getView().setViewName("cambiarUbicacion");
        getView().setValues(keyValues);   // pone el id

        // Cargar datos del paquete
        getView().findObject();

        getView().setKeyEditable(false);
        getView().setTitle("Cambiar ubicación del paquete");
    }
}

