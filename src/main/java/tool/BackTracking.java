package main.java.tool;


import us.lsi.common.List2;
import us.lsi.common.Set2;
import us.lsi.common.String2;

import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

// Caso para maximizar.
public class BackTracking<P extends Problem, S extends Solution, H extends Heuristic<P>> {

    private final State state; // Estado actual.
    private Integer maxValue; // Valor máximo.
    private final Set<S> soluciones; // Soluciones.
    private final H heuristic; // Heurística.
    private final Function<List<Integer>, S> solution; // Función de solución.
    private final P start; // Vértice inicial.

    public BackTracking(Supplier<P> initialVertex, H heuristic, Function<List<Integer>, S> solution) {
        start = initialVertex.get(); // Obtenemos el vértice inicial.
        this.solution = solution; // Función que devuelve la solución.
        this.heuristic = heuristic; // Objeto que devuelve la heurística.
        state = new State(start); // Inicializamos el estado con el vértice inicial.
        maxValue = Integer.MIN_VALUE; // Caso para maximizar.
        soluciones = Set2.empty(); // Para evitar soluciones repetidas.
        search(); // Comienza la búsqueda.
    }

    public static <P extends Problem, S extends Solution, H extends Heuristic<P>> void create(Consumer<String> initialData, Supplier<P> initialVertex, H heuristic, Function<List<Integer>, S> solution, Comparator<S> cmp, String... paths) {
        Locale.setDefault(new Locale("en", "US"));
        System.out.println("#### Algoritmo BackTracking ####");
        for (String path : paths) {
            initialData.accept(path);
            BackTracking<P, S, H> bt = new BackTracking<>(initialVertex, heuristic, solution);
            System.out.println(String2.linea());
            System.out.println("-> Para " + path);
            System.out.println(bt.soluciones.stream().max(cmp).orElse(null));
            System.out.println(String2.linea() + "\n");
        }
    }

    public void search() {
        if (start.goal().test(state.vertex)) { // Si hemos llegado al último vértice.
            Integer value = state.accumulatedValue; // Obtenemos el valor acumulado.
            if (value > maxValue) { // Si es mayor que el valor máximo.
                maxValue = value; // Actualizamos el valor máximo.
                soluciones.add(solution.apply(state.actions)); // Añadimos la solución.
            }
        } else { // Si no hemos llegado al último vértice.
            for (Integer action : state.vertex.actions()) { // Para cada acción.
                double limit = state.accumulatedValue + heuristic.limit(state.vertex, action); // Obtenemos la cota.
                if (limit > maxValue) { // Si es mayor que el valor máximo.
                    state.goForward(action); // Avanzamos.
                    search(); // Continuamos la búsqueda.
                    state.goBack(action); // Retrocedemos.
                }
            }
        }
    }

    public class State {
        private final List<Integer> actions; // Acciones.
        private final List<P> vertices; // Vértices.
        private P vertex; // Vértice actual.
        private Integer accumulatedValue; // Valor acumulado.

        public State(P vertex) {
            // Inicialización de la estructura de datos.
            this.vertex = vertex;
            this.accumulatedValue = 0;
            this.actions = List2.empty();
            this.vertices = List2.of(this.vertex);
        }

        // Avanzamos.
        void goForward(Integer action) {
            actions.add(action); // Añadimos la acción.
            P neighbor = vertex.neighbor(action); // Obtenemos el vecino.
            vertices.add(neighbor); // Añadimos el vértice.
            accumulatedValue += vertex.weight(action); // Actualizamos el valor acumulado.
            vertex = neighbor; // Actualizamos el vértice.
        }

        // Retrocedemos.
        void goBack(Integer a) {
            actions.remove(actions.size() - 1); // Eliminamos la acción.
            vertices.remove(vertices.size() - 1); // Eliminamos el vértice.
            vertex = vertices.get(vertices.size() - 1); // Actualizamos el vértice.
            accumulatedValue -= vertex.weight(a); // Actualizamos el valor acumulado.
        }
    }
}
