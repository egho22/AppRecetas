/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package itson.pruebassfwrecetas;

import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author PC
 */
class RecetaRepositoryTest {

    private RecetaRepository repository;

    @BeforeEach
    void setUp() {
        repository = new RecetaRepository();
    }

    @Test
    void guardar_DeberiaAsignarId_CuandoEsNueva() {
        Receta nueva = new Receta(0, "Pasta", "Italiana", 20);
        Receta guardada = repository.guardar(nueva);

        assertEquals(1, guardada.getId());
        assertTrue(repository.buscarPorId(1).isPresent());
    }

    @Test
    void buscarPorIngrediente_DeberiaSerInsensibleAMayusculas() {
        Receta r = new Receta(0, "Ensalada", "Fit", 10);
        r.getIngredientes().add(new Ingrediente("Tomate", 2, "uds"));
        repository.guardar(r);

        List<Receta> resultado = repository.buscarPorIngrediente("tomate");

        assertEquals(1, resultado.size());
        assertEquals("Ensalada", resultado.get(0).getNombre());
    }

    @Test
    void eliminar_DeberiaRetornarFalse_SiNoExiste() {
        boolean eliminado = repository.eliminar(999);
        assertFalse(eliminado);
    }
}
