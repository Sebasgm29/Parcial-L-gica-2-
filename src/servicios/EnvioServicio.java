package servicios;

import java.util.List;
import modelos.Envio;

public interface EnvioServicio {
    void agregar(String tipo, String codigo, String cliente, double peso, double distancia);
    boolean retirar(String codigo);
    List<Envio> listar();
}