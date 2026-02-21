package itson.pruebassfwrecetas;

/**
 * Clase modelo que representa un ingrediente de una receta.
 * Fiabilidad: validaciones para cantidad y nombre.
 */
public class Ingrediente {

    private String nombre;
    private double cantidad;
    private String unidad; 

    public Ingrediente(String nombre, double cantidad, String unidad) {
        setNombre(nombre);
        setCantidad(cantidad);
        this.unidad = unidad != null ? unidad : "";
    }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) {
        if (nombre == null || nombre.trim().isEmpty())
            throw new IllegalArgumentException("El nombre del ingrediente no puede estar vacío.");
        this.nombre = nombre.trim();
    }

    public double getCantidad() { return cantidad; }
    public void setCantidad(double cantidad) {
        if (cantidad < 0)
            throw new IllegalArgumentException("La cantidad no puede ser negativa.");
        this.cantidad = cantidad;
    }

    public String getUnidad() { return unidad; }
    public void setUnidad(String unidad) { this.unidad = unidad != null ? unidad : ""; }

    @Override
    public String toString() {
        return String.format("%s: %.1f %s", nombre, cantidad, unidad);
    }
}
