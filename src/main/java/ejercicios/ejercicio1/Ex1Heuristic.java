package main.java.ejercicios.ejercicio1;

import java.util.function.Predicate;

public class Ex1Heuristic {

    private Ex1Heuristic() {
    }

    /**
     * Cuenta el número de ficheros que quedan por ser analizados desde el vértice origen.
     *
     * @param source el vértice origen.
     * @param goal   restricción que indica que no quedan más vértices por analizar.
     * @param target el vértice destino.
     * @return un valor entre 0. y el número de ficheros.
     */
    public static Double heuristic(Ex1Problem source, Predicate<Ex1Problem> goal, Ex1Problem target) {
        return (DataEx1.getNumFichero() - source.id()) * 1.0;
    }

    public static Double cota(Ex1Problem v, Integer a) {
        return heuristic(v.neighbor(a), null, null) + v.weight(a);
    }
}
