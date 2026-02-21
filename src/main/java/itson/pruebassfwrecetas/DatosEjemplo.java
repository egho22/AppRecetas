package itson.pruebassfwrecetas;

/**
 * Inicializador de datos de ejemplo para demostración de la aplicación.
 */
public class DatosEjemplo {

    public static void cargar(RecetaService servicio) {

        // --- Receta 1: Pasta Carbonara ---
        Receta carbonara = new Receta(0, "Pasta Carbonara", "Italiana", 25);
        carbonara.agregarIngrediente(new Ingrediente("Espagueti", 200, "gramos"));
        carbonara.agregarIngrediente(new Ingrediente("Panceta", 100, "gramos"));
        carbonara.agregarIngrediente(new Ingrediente("Huevo", 2, "unidades"));
        carbonara.agregarIngrediente(new Ingrediente("Queso parmesano", 50, "gramos"));
        carbonara.agregarIngrediente(new Ingrediente("Pimienta negra", 1, "cucharadita"));
        carbonara.agregarPaso("Cocer la pasta en agua con sal hasta que esté al dente.");
        carbonara.agregarPaso("Freír la panceta en una sartén hasta que esté crujiente.");
        carbonara.agregarPaso("Batir los huevos con el queso rallado y pimienta negra.");
        carbonara.agregarPaso("Mezclar la pasta caliente con la panceta fuera del fuego.");
        carbonara.agregarPaso("Agregar la mezcla de huevo y remover rápidamente para que no cuaje.");
        carbonara.agregarPaso("Servir inmediatamente con más queso y pimienta.");
        carbonara.setFavorita(true);
        servicio.guardarReceta(carbonara);

        // --- Receta 2: Tacos de Pollo ---
        Receta tacos = new Receta(0, "Tacos de Pollo", "Mexicana", 30);
        tacos.agregarIngrediente(new Ingrediente("Pechuga de pollo", 300, "gramos"));
        tacos.agregarIngrediente(new Ingrediente("Tortillas de maíz", 6, "unidades"));
        tacos.agregarIngrediente(new Ingrediente("Cebolla", 1, "unidades"));
        tacos.agregarIngrediente(new Ingrediente("Cilantro", 20, "gramos"));
        tacos.agregarIngrediente(new Ingrediente("Limón", 2, "unidades"));
        tacos.agregarIngrediente(new Ingrediente("Chile jalapeño", 1, "unidades"));
        tacos.agregarPaso("Sazonar el pollo con sal, pimienta y comino.");
        tacos.agregarPaso("Cocinar el pollo a la parrilla o sartén hasta que esté dorado.");
        tacos.agregarPaso("Desmenuzar el pollo cocido.");
        tacos.agregarPaso("Calentar las tortillas en comal o sartén.");
        tacos.agregarPaso("Armar los tacos con pollo, cebolla, cilantro y jalapeño.");
        tacos.agregarPaso("Exprimir limón al gusto y servir.");
        servicio.guardarReceta(tacos);

        // --- Receta 3: Ensalada César ---
        Receta cesar = new Receta(0, "Ensalada César", "Americana", 15);
        cesar.agregarIngrediente(new Ingrediente("Lechuga romana", 1, "unidades"));
        cesar.agregarIngrediente(new Ingrediente("Queso parmesano", 30, "gramos"));
        cesar.agregarIngrediente(new Ingrediente("Crutones", 50, "gramos"));
        cesar.agregarIngrediente(new Ingrediente("Aderezo César", 3, "cucharadas"));
        cesar.agregarIngrediente(new Ingrediente("Limón", 1, "unidades"));
        cesar.agregarPaso("Lavar y trozar la lechuga romana.");
        cesar.agregarPaso("Mezclar la lechuga con el aderezo César.");
        cesar.agregarPaso("Agregar crutones y queso parmesano rallado.");
        cesar.agregarPaso("Exprimir jugo de limón y servir frío.");
        cesar.setFavorita(true);
        servicio.guardarReceta(cesar);

        // --- Receta 4: Sopa de Tomate ---
        Receta sopa = new Receta(0, "Sopa de Tomate", "Mediterránea", 40);
        sopa.agregarIngrediente(new Ingrediente("Tomates", 500, "gramos"));
        sopa.agregarIngrediente(new Ingrediente("Cebolla", 1, "unidades"));
        sopa.agregarIngrediente(new Ingrediente("Ajo", 2, "dientes"));
        sopa.agregarIngrediente(new Ingrediente("Caldo de verduras", 500, "ml"));
        sopa.agregarIngrediente(new Ingrediente("Aceite de oliva", 2, "cucharadas"));
        sopa.agregarIngrediente(new Ingrediente("Albahaca", 10, "gramos"));
        sopa.agregarPaso("Sofreír la cebolla y el ajo en aceite de oliva.");
        sopa.agregarPaso("Agregar los tomates troceados y cocinar 10 minutos.");
        sopa.agregarPaso("Añadir el caldo y dejar cocinar 20 minutos a fuego medio.");
        sopa.agregarPaso("Triturar con licuadora hasta obtener textura lisa.");
        sopa.agregarPaso("Rectificar sal y servir con albahaca fresca.");
        servicio.guardarReceta(sopa);

        System.out.println("[Sistema] " + 4 + " recetas de ejemplo cargadas correctamente.");
    }
}
