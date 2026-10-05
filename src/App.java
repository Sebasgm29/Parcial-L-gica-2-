import controladores.LogisticaControlador;
import servicios.EnvioServicio;
import servicios.EnvioServicioImpl;
import vistas.LogisticaVista;

public class App {
    public static void main(String[] args) {
        // 1. Se crean las tres piezas
        LogisticaVista vista = new LogisticaVista();
        EnvioServicio servicio = new EnvioServicioImpl();

        // 2. El controlador las conecta
        new LogisticaControlador(vista, servicio);

        // 3. Se muestra la ventana
        vista.setVisible(true);
    }
}