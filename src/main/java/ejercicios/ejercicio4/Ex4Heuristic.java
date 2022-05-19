package main.java.ejercicios.ejercicio4;

import java.util.Objects;
import java.util.function.Predicate;

public class Ex4Heuristic {

    private Ex4Heuristic() {
    }

    /**
     * Obtiene el número de contenedores que ya están llenos..
     *
     * @param source el vértice origen.
     * @param goal   restricción que indica que no quedan más vértices por analizar.
     * @param target el vértice destino.
     * @return número de contenedores llenos.
     */
    public static Double heuristic(Ex4Problem source, Predicate<Ex4Problem> goal, Ex4Problem target) {
        if (Objects.equals(source.id(), DataEx4.getNumElementos())) return 0.;
        return Math.min(DataEx4.getNumContenedores() - DataEx4.getNumeroContenedoresLLenos(source.capacidadRestante()),
                DataEx4.getNumElementos() - source.id()) * 1.0;
    }

    public static Double cota(Ex4Problem v, Integer a) {
        return heuristic(v.neighbor(a), null, null) + v.weight(a);
    }
}
