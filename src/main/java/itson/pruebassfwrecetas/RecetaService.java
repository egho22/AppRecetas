package itson.pruebassfwrecetas;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Servicio de lógica de negocio para la aplicación de recetas.
 * 
 */
public class RecetaService {

    private final RecetaRepository repositorio;

    public RecetaService(RecetaRepository repositorio) {
        this.repositorio = repositorio;
    }

    // ===================== BÚSQUEDA =====================

    /**
     * Busca recetas por ingrediente (búsqueda parcial, insensible a mayúsculas).
     */
    public List<Receta> buscarPorIngrediente(String ingrediente) {
        return repositorio.buscarPorIngrediente(ingrediente);
    }

    /**
     * Busca recetas por tipo de cocina.
     */
    public List<Receta> buscarPorTipoCocina(String tipoCocina) {
        return repositorio.buscarPorTipoCocina(tipoCocina);
    }

    /**
     * Busca recetas cuyo tiempo de preparación no supere el indicado.
     */
    public List<Receta> buscarPorTiempoMaximo(int minutos) {
        if (minutos < 0) {
            throw new IllegalArgumentException("El tiempo máximo debe ser un valor positivo.");
        }
        return repositorio.buscarPorTiempoMaximo(minutos);
    }

    /**
     * Retorna todas las recetas disponibles.
     */
    public List<Receta> obtenerTodasLasRecetas() {
        return repositorio.obtenerTodas();
    }

    /**
     * Busca una receta por su ID.
     */
    public Optional<Receta> buscarPorId(int id) {
        return repositorio.buscarPorId(id);
    }

    // ===================== GUARDADO / FAVORITOS =====================

    /**
     * Guarda o actualiza una receta.
     */
    public Receta guardarReceta(Receta receta) {
        if (receta == null) throw new IllegalArgumentException("La receta no puede ser nula.");
        return repositorio.guardar(receta);
    }

    /**
     * Marca o desmarca una receta como favorita.
     * @return true si la operación fue exitosa.
     */
    public boolean toggleFavorita(int idReceta) {
        Optional<Receta> opt = repositorio.buscarPorId(idReceta);
        if (opt.isEmpty()) return false;
        Receta r = opt.get();
        r.setFavorita(!r.isFavorita());
        repositorio.guardar(r);
        return true;
    }

    /**
     * Retorna todas las recetas marcadas como favoritas.
     */
    public List<Receta> obtenerFavoritas() {
        return repositorio.obtenerFavoritas();
    }

    /**
     * Elimina una receta por ID.
     */
    public boolean eliminarReceta(int id) {
        return repositorio.eliminar(id);
    }

    // ===================== LISTA DE COMPRAS =====================

    /**
     * Genera una lista de compras consolidada a partir de una lista de IDs de recetas.
     * Si el mismo ingrediente aparece en varias recetas, se suman las cantidades.
     * 
     * Funcionalidad: automatización de lista de compras requerida.
     * 
     * @param idsRecetas IDs de las recetas seleccionadas.
     * @return Mapa de nombre de ingrediente -> total necesario (cantidad + unidad).
     */
    public Map<String, String> generarListaDeCompras(List<Integer> idsRecetas) {
        if (idsRecetas == null || idsRecetas.isEmpty()) {
            return Collections.emptyMap();
        }

        // clave: "nombre|unidad", valor: cantidad acumulada
        Map<String, Double> acumulado = new LinkedHashMap<>();
        Map<String, String> unidades = new LinkedHashMap<>();

        for (int idReceta : idsRecetas) {
            Optional<Receta> opt = repositorio.buscarPorId(idReceta);
            opt.ifPresent(receta -> {
                for (Ingrediente ing : receta.getIngredientes()) {
                    String clave = ing.getNombre().toLowerCase() + "|" + ing.getUnidad();
                    acumulado.merge(clave, ing.getCantidad(), Double::sum);
                    unidades.putIfAbsent(clave, ing.getUnidad());
                }
            });
        }

        // Construir resultado legible
        Map<String, String> resultado = new LinkedHashMap<>();
        for (Map.Entry<String, Double> entry : acumulado.entrySet()) {
            String[] partes = entry.getKey().split("\\|", 2);
            String nombreIngrediente = partes[0];
            String unidad = unidades.getOrDefault(entry.getKey(), "");
            String valor = String.format("%.1f %s", entry.getValue(), unidad).trim();
            resultado.put(nombreIngrediente, valor);
        }
        return resultado;
    }

    // ===================== INSTRUCCIONES =====================

    /**
     * Retorna los pasos de preparación de una receta de forma numerada.
     */
    public List<String> obtenerInstruccionesPasoAPaso(int idReceta) {
        Optional<Receta> opt = repositorio.buscarPorId(idReceta);
        if (opt.isEmpty()) return Collections.emptyList();
        List<String> pasos = opt.get().getPasos();
        List<String> resultado = new ArrayList<>();
        for (int i = 0; i < pasos.size(); i++) {
            resultado.add("Paso " + (i + 1) + ": " + pasos.get(i));
        }
        return resultado;
    }
}
