package main.java.ejercicios.ejercicio1;

import java.util.function.Predicate;

public class HeuristicEjercicio1 {

    /**
     * Cuenta el número de ficheros que quedan por ser analizados desde el vértice origen.
     *
     * @param source el vértice origen.
     * @param goal   restricción que indica que no quedan más vértices por analizar.
     * @param target el vértice destino.
     * @return un valor entre 0. y el número de ficheros.
     */
    public static Double heuristic(ProblemEjercicio1 source, Predicate<ProblemEjercicio1> goal, ProblemEjercicio1 target) {
        return (DataEjercicio1.getNumFichero() - source.indice()) * 1.0;
    }

    public static Double cota(ProblemEjercicio1 v, Integer a) {
        return heuristic(v.neighbor(a), null, null) + (a < DataEjercicio1.getNumFichero() ? 1. : 0.);
    }

    private HeuristicEjercicio1() {
    }
}
