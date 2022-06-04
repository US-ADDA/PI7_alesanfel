package main.java.tool;

import us.lsi.common.List2;
import us.lsi.common.Map2;
import us.lsi.common.String2;

import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

// Caso para maximizar.
public class DynamicProgramming<P extends Problem, S extends Solution, H extends Heuristic<P>> {

    private Integer value;
    private final P start; // Para el nodo inicial.
    private final Map<P, SubProblem> memory; // Para la memoria.
    private final H heuristic; // Para la heurística.
    private final Function<List<Integer>, S> solution; // Para la función de solución.

    public DynamicProgramming(Supplier<P> initialVertex, H heuristic, Function<List<Integer>, S> solution) {
        value = Integer.MIN_VALUE; // Inicializamos el valor.
        start = initialVertex.get(); // Inicializamos el nodo inicial.
        memory = Map2.empty(); // Inicializamos la memoria.
        this.heuristic = heuristic; // Inicializamos la heurística.
        this.solution = solution; // Inicializamos la función de solución.
        search(start, 0, memory); // Buscamos el nodo inicial.
        solution(); // Mostramos la solución.
    }

    public static <P extends Problem, S extends Solution, H extends Heuristic<P>> void create(Consumer<String> initialData, Supplier<P> initialVertex, H heuristic, Function<List<Integer>, S> solution, String... paths) {
        Locale.setDefault(new Locale("en", "US"));
        System.out.println("#### Algoritmo DynamicProgramming ####");
        for (String path : paths) {
            initialData.accept(path);
            System.out.println(String2.linea());
            DynamicProgramming<P, S, H> pd = new DynamicProgramming<>(initialVertex, heuristic, solution);
            System.out.println("-> Para " + path);
            System.out.println(pd.solution());
            System.out.println(String2.linea() + "\n");
        }
    }

    public SubProblem search(P vertex, Integer accumulatedValue, Map<P, SubProblem> memory) {
        SubProblem result; // Para el resultado.
        if (memory.containsKey(vertex)) // Si ya tenemos el nodo en la memoria.
            result = memory.get(vertex); // Obtenemos el resultado.
        else if (start.goal().test(vertex)) { // Si es el último vértice.
            result = SubProblem.of(null, 0); // Damos un valor a resultado.
            memory.put(vertex, result); // Guardamos el resultado en la memoria.
            if (accumulatedValue > value) value = accumulatedValue; // Actualizamos el valor.
        } else { // Si no es el último vértice.
            List<SubProblem> soluciones = List2.empty(); // Para las soluciones.
            for (Integer action : vertex.actions()) { // Para cada acción.
                double limit = accumulatedValue * 1.0 + heuristic.limit(vertex, action); // Calculamos la cota.
                if (limit <= value) continue; // Si la cota es menor que el valor, no seguimos.
                SubProblem s = search(vertex.neighbor(action), accumulatedValue + vertex.weight(action), memory); // Buscamos el siguiente vértice.
                if (s != null) { // Si no es nulo.
                    SubProblem sp = SubProblem.of(action, (s.weight() + vertex.weight(action))); // Calculamos el peso.
                    soluciones.add(sp); // Añadimos la solución.
                }
            }
            result = soluciones.stream().max(Comparator.naturalOrder()).orElse(null); // Obtenemos la solución.
            if (result != null) // Si no es nulo.
                memory.put(vertex, result); // Guardamos el resultado en la memoria.
        }
        return result; // Devolvemos el resultado.
    }

    public S solution() {
        List<Integer> actions = List2.empty();
        P v = start;
        SubProblem s = memory.get(v);
        while (s.action() != null) {
            actions.add(s.action());
            v = v.neighbor(s.action());
            s = memory.get(v);
        }
        return solution.apply(actions);
    }

    public record SubProblem(Integer action, Integer weight) implements Comparable<SubProblem> {

        public static SubProblem of(Integer action, Integer weight) {
            return new SubProblem(action, weight);
        }

        @Override
        public int compareTo(SubProblem sp) {
            return this.weight.compareTo(sp.weight);
        }
    }
}
