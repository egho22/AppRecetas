package itson.pruebassfwrecetas;

import java.util.List;
import java.util.ArrayList;

/**
 * Clase modelo que representa una receta de cocina.
 *
 */
public class Receta {

    private int id;
    private String nombre;
    private String tipoCocina;
    private int tiempoPreparacion;
    private List<Ingrediente> ingredientes;
    private List<String> pasos;
    private boolean favorita;

    public Receta() {
        this.ingredientes = new ArrayList<>();
        this.pasos = new ArrayList<>();
        this.favorita = false;
    }

    public Receta(int id, String nombre, String tipoCocina, int tiempoPreparacion) {
        this();
        this.id = id;
        setNombre(nombre);
        setTipoCocina(tipoCocina);
        setTiempoPreparacion(tiempoPreparacion);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre de la receta no puede estar vacío.");
        }
        this.nombre = nombre.trim();
    }

    public String getTipoCocina() {
        return tipoCocina;
    }

    public void setTipoCocina(String tipoCocina) {
        if (tipoCocina == null || tipoCocina.trim().isEmpty()) {
            throw new IllegalArgumentException("El tipo de cocina no puede estar vacío.");
        }
        this.tipoCocina = tipoCocina.trim();
    }

    public int getTiempoPreparacion() {
        return tiempoPreparacion;
    }

    public void setTiempoPreparacion(int tiempoPreparacion) {
        if (tiempoPreparacion < 0) {
            throw new IllegalArgumentException("El tiempo de preparación no puede ser negativo.");
        }
        this.tiempoPreparacion = tiempoPreparacion;
    }

    public List<Ingrediente> getIngredientes() {
        return ingredientes;
    }

    public void setIngredientes(List<Ingrediente> ingredientes) {
        this.ingredientes = ingredientes != null ? ingredientes : new ArrayList<>();
    }

    public List<String> getPasos() {
        return pasos;
    }

    public void setPasos(List<String> pasos) {
        this.pasos = pasos != null ? pasos : new ArrayList<>();
    }

    public boolean isFavorita() {
        return favorita;
    }

    public void setFavorita(boolean favorita) {
        this.favorita = favorita;
    }

    public void agregarIngrediente(Ingrediente ingrediente) {
        if (ingrediente != null) {
            this.ingredientes.add(ingrediente);
        }
    }

    public void agregarPaso(String paso) {
        if (paso != null && !paso.trim().isEmpty()) {
            this.pasos.add(paso.trim());
        }
    }

    @Override
    public String toString() {
        return String.format("[ID:%d] %s | Cocina: %s | Tiempo: %d min | Favorita: %s",
                id, nombre, tipoCocina, tiempoPreparacion, favorita ? "Sí" : "No");
    }
}
