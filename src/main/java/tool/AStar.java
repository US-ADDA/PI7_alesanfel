package main.java.tool;

import org.jheaps.AddressableHeap.Handle;
import org.jheaps.tree.FibonacciHeap;
import us.lsi.common.List2;
import us.lsi.common.Map2;
import us.lsi.common.String2;

import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

// Caso para maximizar.
public class AStar<P extends Problem, S extends Solution, H extends Heuristic<P>> {

    private final Map<P, Handle<Double, SubAStar<P>>> tree; // Para el árbol de prioridades.
    private final FibonacciHeap<Double, SubAStar<P>> heap; // Para el heap de prioridades.
    private final H heuristic; // Para la heurística.
    private final Function<List<Integer>, S> solution; // Para la función de solución.
    private Boolean goal; // Para saber si hemos encontrado el objetivo.

    public AStar(Supplier<P> initialVertex, H heuristic, Function<List<Integer>, S> solution) {
        P start = initialVertex.get(); // Inicializamos el nodo inicial.
        Double distanceToEnd = heuristic.heuristic(start, null, null); // Calculamos la distancia al final.
        SubAStar<P> a = SubAStar.of(start, null, null, 0.); // Creamos el nodo inicial.
        this.heap = new FibonacciHeap<>(); // Creamos el heap.
        Handle<Double, SubAStar<P>> handle = heap.insert(distanceToEnd, a); // Insertamos el nodo inicial en el heap.
        this.heuristic = heuristic; // Inicializamos la heurística.
        this.solution = solution; // Inicializamos la solución.
        this.tree = Map2.empty(); // Inicializamos el árbol.
        this.tree.put(start, handle); // El nodo inicial está en el árbol.
        this.goal = false; // No hemos encontrado el nodo final.
    }

    public static <P extends Problem, S extends Solution, H extends Heuristic<P>> void create(Consumer<String> initialData, Supplier<P> initialVertex, H heuristic, Function<List<Integer>, S> solution, String... path) {
        Locale.setDefault(new Locale("en", "US"));
        System.out.println("#### Algoritmo A* ####");
        for (var i = 0; i < path.length; i++) {
            initialData.accept(path[i]);
            AStar<P, S, H> aStar = new AStar<>(initialVertex, heuristic, solution);
            System.out.println(String2.linea());
            System.out.println("-> Para " + path[i]);
            System.out.println(aStar.solution(aStar.search()));
            System.out.println(String2.linea() + "\n");
        }
    }

    private List<Integer> actions(P v) {
        List<Integer> ls = List2.empty(); // Lista de acciones
        Integer action;
        try {
            action = tree.get(v).getValue().action(); // Acción
        } catch (Exception e) {
            return ls;
        }
        while (action != null) { // Mientras no sea null
            ls.add(action); // Agregar acción action la lista
            v = tree.get(v).getValue().lastVertex(); // Obtener ultimo vértice
            action = tree.get(v).getValue().action(); // Obtener acción
        }
        Collections.reverse(ls); // Invertir lista
        return ls; // Retornar lista
    }


    public List<Integer> search() {
        P currentVertex; // Vértice actual.
        P solutionVertex = null; // Vértice de la solución.
        while (!heap.isEmpty() && Boolean.FALSE.equals(goal)) { // Mientras no se vacíe el heap y no se haya llegado al último vértice.
            Handle<Double, SubAStar<P>> handle = heap.deleteMin(); // Saca del montón y devuelve el subproblema con peso mínimo.
            SubAStar<P> dataActual = handle.getValue(); // Obtiene el valor del nodo.
            currentVertex = dataActual.vertex(); // Obtiene el vértice del nodo.
            if (currentVertex.constraints() && currentVertex.goal())
                solutionVertex = currentVertex; // Si el vértice actual cumple las restricciones y es el último.
            for (Integer action : currentVertex.actions()) { // Recorre las acciones del vértice.
                P neighbor = currentVertex.neighbor(action); // Obtiene el vecino de la acción.
                Double newDistance = dataActual.distanceToOrigin() - currentVertex.weight(action); // Calcula la nueva distancia, (siempre es negativa).
                Double newDistanceToEnd = newDistance - heuristic.heuristic(neighbor, null, null); // Calcula la distancia al final.
                if (!tree.containsKey(neighbor)) { // Si no está en el árbol.
                    SubAStar<P> ac = SubAStar.of(neighbor, action, currentVertex, newDistance); // Crea el nodo.
                    Handle<Double, SubAStar<P>> neighborHandle = heap.insert(newDistanceToEnd, ac); // Inserta el nodo en la cola.
                    tree.put(neighbor, neighborHandle); // Inserta el nodo en el árbol.
                } else if (newDistance < tree.get(neighbor).getValue().distanceToOrigin()) { // Si la distancia es menor que la del árbol.
                    SubAStar<P> ac = SubAStar.of(neighbor, action, currentVertex, newDistance); // Crea el nodo.
                    Handle<Double, SubAStar<P>> heapVertex = tree.get(neighbor); // Obtiene el nodo del árbol.
                    heapVertex.setValue(ac); // Cambia el valor del nodo.
                    heapVertex.decreaseKey(newDistanceToEnd); // Cambia la distancia hasta el final del nodo y reordena la cola.
                }
            }
            goal = currentVertex.goal(); // Comprueba si hemos terminado.
        }
        return actions(solutionVertex); // Devuelve las acciones.
    }

    public S solution(List<Integer> actions) {
        return solution.apply(actions);
    }

    public record SubAStar<P extends Problem>(P vertex, Integer action, P lastVertex, Double distanceToOrigin) {
        public static <P extends Problem> SubAStar<P> of(P vertex, Integer action, P lastVertex,
                                                         Double distanceToOrigin) {
            return new SubAStar<>(vertex, action, lastVertex, distanceToOrigin);
        }
    }
}
