/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package itson.pruebassfwrecetas;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 *
 * @author PC
 */
@ExtendWith(MockitoExtension.class)
public class RecetaServiceTest {

    @Mock
    private RecetaRepository repository;

    @InjectMocks
    private RecetaService service;

    @Test
    void generarListaDeCompras_DeberiaConsolidarIngredientesRepetidos() {
        // Preparar datos de prueba
        Receta r1 = new Receta(1, "Receta 1", "Tipo", 10);
        r1.getIngredientes().add(new Ingrediente("Sal", 5.0, "gr"));

        Receta r2 = new Receta(2, "Receta 2", "Tipo", 10);
        r2.getIngredientes().add(new Ingrediente("sal", 10.0, "gr")); // mismo nombre, distinta capitalización

        // Configurar comportamiento del Mock
        when(repository.buscarPorId(1)).thenReturn(Optional.of(r1));
        when(repository.buscarPorId(2)).thenReturn(Optional.of(r2));

        // Ejecutar
        Map<String, String> lista = service.generarListaDeCompras(Arrays.asList(1, 2));

        // Verificar: 5.0 + 10.0 = 15.0
        assertTrue(lista.containsKey("sal"));
        assertEquals("15.0 gr", lista.get("sal"));
    }

    @Test
    void toggleFavorita_DeberiaInvertirEstado() {
        Receta receta = new Receta(1, "Pizza", "Italiana", 30);
        receta.setFavorita(false);

        when(repository.buscarPorId(1)).thenReturn(Optional.of(receta));

        boolean resultado = service.toggleFavorita(1);

        assertTrue(resultado);
        assertTrue(receta.isFavorita());
        verify(repository, times(1)).guardar(receta);
    }

    @Test
    void buscarPorTiempoMaximo_DeberiaLanzarExcepcion_SiMinutosNegativos() {
        assertThrows(IllegalArgumentException.class, () -> {
            service.buscarPorTiempoMaximo(-5);
        });
    }
}
