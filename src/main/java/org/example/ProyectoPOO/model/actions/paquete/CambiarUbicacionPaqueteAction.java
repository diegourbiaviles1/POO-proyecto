package org.example.ProyectoPOO.model.actions.paquete;

import org.openxava.actions.TabBaseAction;

import java.util.Map;

public class CambiarUbicacionPaqueteAction extends TabBaseAction {

    @Override
    public void execute() throws Exception {

        // Tomar la selección de la lista
        Map[] seleccionados = getSelectedKeys();
        if (seleccionados == null || seleccionados.length == 0) {
            addError("Debe seleccionar al menos un registro");
            return;
        }

        // Solo trabajamos con el primer paquete seleccionado
        Map keyValues = seleccionados[0]; // contiene { id = ... }

        // Abrir el diálogo
        showDialog(); // a partir de aquí getView() apunta al diálogo

        // Configurar la vista del diálogo
        getView().setModelName("Paquete");
        getView().setViewName("cambiarUbicacion");
        getView().setValues(keyValues);   // pone el id del paquete
        getView().findObject();           // carga el paquete desde la BD

        getView().setKeyEditable(false);
        getView().setTitle("Cambiar ubicación del paquete");

        // ACCIONES (botones) del diálogo: usar el controlador CambioUbicacionPaquete
        setControllers("CambioUbicacionPaquete");
    }
}
