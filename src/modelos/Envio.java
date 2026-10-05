package modelos;

public abstract class Envio {
    private String codigo;
    private String cliente;
    private double peso;       // Kg
    private double distancia;  // Km

    public Envio(String codigo, String cliente, double peso, double distancia) {
        this.codigo = codigo;
        this.cliente = cliente;
        this.peso = peso;
        this.distancia = distancia;
    }

    public String getCodigo() { return codigo; }
    public String getCliente() { return cliente; }
    public double getPeso() { return peso; }
    public double getDistancia() { return distancia; }

    // POLIMORFISMO: cada subclase implementa su propia tarifa
    public abstract double calcularTarifa();

    // Nombre del medio de transporte (también polimórfico)
    public abstract String getTipo();
}