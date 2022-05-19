package main.java.ejercicios.ejercicio2;

import java.util.function.Predicate;
import java.util.stream.IntStream;

public class Ex2Heuristic {

    private Ex2Heuristic() {
    }

    /**
     * Suma la valoración de todos los candidatos que aún no han sido analizados.
     *
     * @param source el vértice origen.
     * @param goal   restricción que indica que no quedan más vértices por analizar.
     * @param target el vértice destino.
     * @return la valoración total menos los candidatos ya analizados.
     */
    public static Double heuristic(Ex2Problem source, Predicate<Ex2Problem> goal, Ex2Problem target) {
        return IntStream.range(source.id(), DataEx2.getNumCandidatos())
                .map(DataEx2::getValoracion)
                .sum() * 1.0;
    }

    public static Double cota(Ex2Problem v, Integer a) {
        return heuristic(v, null, null) + v.weight(a);
    }
}
