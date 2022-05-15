package main.java.ejercicios.ejercicio4;

import java.util.function.Predicate;

public class HeuristicEjercicio4 {

    /**
     * Obtiene el número de contenedores que ya están llenos..
     *
     * @param source el vértice origen.
     * @param goal   restricción que indica que no quedan más vértices por analizar.
     * @param target el vértice destino.
     * @return número de contenedores llenos.
     */
    public static Double heuristic(ProblemEjercicio4 source, Predicate<ProblemEjercicio4> goal, ProblemEjercicio4 target) {
        return source.weight();
    }

    private HeuristicEjercicio4() {
    }
}
