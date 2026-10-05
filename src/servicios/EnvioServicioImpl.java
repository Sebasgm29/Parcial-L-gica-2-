package servicios;

import java.util.ArrayList;
import java.util.List;
import modelos.Aereo;
import modelos.Envio;
import modelos.Maritimo;
import modelos.Terrestre;

public class EnvioServicioImpl implements EnvioServicio {

    // Aquí vive la colección: el controlador y la vista nunca la tocan directamente
    private final List<Envio> envios = new ArrayList<>();

    @Override
    public void agregar(String tipo, String codigo, String cliente, double peso, double distancia) {
        // Reglas de negocio
        if (codigo == null || codigo.trim().isEmpty() || cliente == null || cliente.trim().isEmpty()) {
            throw new IllegalArgumentException("Debes ingresar el número y el cliente.");
        }
        if (peso <= 0 || distancia <= 0) {
            throw new IllegalArgumentException("Peso y distancia deben ser mayores que cero.");
        }
        for (Envio e : envios) {
            if (e.getCodigo().equals(codigo.trim())) {
                throw new IllegalArgumentException("Ya existe un envío con ese número.");
            }
        }

        // La variable es de tipo Envio; el objeto real depende del tipo elegido
        Envio envio;
        switch (tipo) {
            case "Terrestre": envio = new Terrestre(codigo.trim(), cliente.trim(), peso, distancia); break;
            case "Aéreo":     envio = new Aereo(codigo.trim(), cliente.trim(), peso, distancia); break;
            case "Marítimo":  envio = new Maritimo(codigo.trim(), cliente.trim(), peso, distancia); break;
            default: throw new IllegalArgumentException("Tipo de envío no válido.");
        }
        envios.add(envio);
    }

    @Override
    public boolean retirar(String codigo) {
        return envios.removeIf(e -> e.getCodigo().equals(codigo));
    }

    @Override
    public List<Envio> listar() {
        return new ArrayList<>(envios); // copia, para proteger la lista interna
    }
}