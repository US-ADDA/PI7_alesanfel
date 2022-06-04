package main.java.ejercicios.ejercicio4;

import main.java.tool.Heuristic;

import java.util.Objects;
import java.util.function.Predicate;

public class Ex4Heuristic implements Heuristic<Ex4Problem> {

    public Ex4Heuristic() {
    }

    /**
     * Obtiene el número de contenedores que ya están llenos..
     *
     * @param source el vértice origen.
     * @param goal   restricción que indica que no quedan más vértices por analizar.
     * @param target el vértice destino.
     * @return número de contenedores llenos.
     */
    public Double heuristic(Ex4Problem source, Predicate<Ex4Problem> goal, Ex4Problem target) {
        if (Objects.equals(source.id(), DataEx4.getNumElementos())) return 0.;
        return Math.min(DataEx4.getNumContenedores() - DataEx4.getNumeroContenedoresLLenos(source.capacidadRestante()),
                DataEx4.getNumElementos() - source.id()) * 1.0;
    }

    public Double limit(Ex4Problem v, Integer a) {
        return heuristic(v.neighbor(a), null, null) + v.weight(a);
    }
}
