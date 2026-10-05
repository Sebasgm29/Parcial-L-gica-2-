package controladores;

import java.util.List;
import modelos.Envio;
import servicios.EnvioServicio;
import vistas.LogisticaVista;

// Conecta la vista con el servicio: recibe las acciones, delega y actualiza la pantalla
public class LogisticaControlador {

    private final LogisticaVista vista;
    private final EnvioServicio servicio;

    public LogisticaControlador(LogisticaVista vista, EnvioServicio servicio) {
        this.vista = vista;
        this.servicio = servicio;
        this.vista.setControlador(this);   // la vista ya puede avisarle al controlador
        actualizarTabla();
    }

    // Se llama cuando el usuario presiona "Guardar"
    public void guardar() {
        double peso, distancia;
        try {
            peso = Double.parseDouble(vista.getPeso().trim());
            distancia = Double.parseDouble(vista.getDistancia().trim());
        } catch (NumberFormatException ex) {
            vista.mostrarMensaje("Peso y distancia deben ser números.");
            return;
        }

        try {
            servicio.agregar(vista.getTipo(), vista.getNumero(), vista.getCliente(), peso, distancia);
            vista.limpiarFormulario();
            vista.ocultarFormulario();
            actualizarTabla();
        } catch (IllegalArgumentException ex) {
            vista.mostrarMensaje(ex.getMessage());   // error de negocio que lanzó el servicio
        }
    }

    // Se llama cuando el usuario presiona el botón de quitar envío
    public void quitar() {
        int fila = vista.getFilaSeleccionada();
        if (fila < 0) {
            vista.mostrarMensaje("Selecciona un envío de la tabla para quitarlo.");
            return;
        }
        List<Envio> envios = servicio.listar();
        servicio.retirar(envios.get(fila).getCodigo());
        actualizarTabla();
    }

    private void actualizarTabla() {
        vista.mostrarEnvios(servicio.listar());
    }
}