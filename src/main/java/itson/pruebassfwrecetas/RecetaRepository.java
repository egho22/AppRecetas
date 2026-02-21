package itson.pruebassfwrecetas;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Repositorio en memoria para gestión de recetas.
 *
 */
public class RecetaRepository {

    private final Map<Integer, Receta> recetas = new LinkedHashMap<>();
    private int contadorId = 1;

    /**
     * Guarda o actualiza una receta en el repositorio.
     */
    public Receta guardar(Receta receta) {
        if (receta == null) {
            throw new IllegalArgumentException("La receta no puede ser nula.");
        }
        if (receta.getId() == 0) {
            receta.setId(contadorId++);
        }
        recetas.put(receta.getId(), receta);
        return receta;
    }

    /**
     * Elimina una receta por ID.
     *
     * @return true si fue eliminada, false si no existía.
     */
    public boolean eliminar(int id) {
        return recetas.remove(id) != null;
    }

    /**
     * Busca una receta por ID.
     */
    public Optional<Receta> buscarPorId(int id) {
        return Optional.ofNullable(recetas.get(id));
    }

    /**
     * Retorna todas las recetas.
     */
    public List<Receta> obtenerTodas() {
        return new ArrayList<>(recetas.values());
    }

    /**
     * Retorna todas las recetas marcadas como favoritas.
     */
    public List<Receta> obtenerFavoritas() {
        return recetas.values().stream()
                .filter(Receta::isFavorita)
                .collect(Collectors.toList());
    }

    /**
     * Busca recetas que contengan el ingrediente dado.
     */
    public List<Receta> buscarPorIngrediente(String nombreIngrediente) {
        if (nombreIngrediente == null || nombreIngrediente.trim().isEmpty()) {
            return new ArrayList<>();
        }
        String busqueda = nombreIngrediente.toLowerCase().trim();
        return recetas.values().stream()
                .filter(r -> r.getIngredientes().stream()
                .anyMatch(i -> i.getNombre().toLowerCase().contains(busqueda)))
                .collect(Collectors.toList());
    }

    /**
     * Busca recetas por tipo de cocina.
     */
    public List<Receta> buscarPorTipoCocina(String tipoCocina) {
        if (tipoCocina == null || tipoCocina.trim().isEmpty()) {
            return new ArrayList<>();
        }
        String busqueda = tipoCocina.toLowerCase().trim();
        return recetas.values().stream()
                .filter(r -> r.getTipoCocina().toLowerCase().contains(busqueda))
                .collect(Collectors.toList());
    }

    /**
     * Busca recetas con tiempo de preparación menor o igual al indicado.
     */
    public List<Receta> buscarPorTiempoMaximo(int minutos) {
        if (minutos < 0) {
            return new ArrayList<>();
        }
        return recetas.values().stream()
                .filter(r -> r.getTiempoPreparacion() <= minutos)
                .collect(Collectors.toList());
    }
}
