package main.java.ejercicios.ejercicio3;

import java.util.Objects;
import java.util.function.Predicate;
import java.util.stream.IntStream;

public class Ex3Heuristic {

    private Ex3Heuristic() {
    }

    /**
     * Suma el beneficio de todos los productos que aún no han sido analizados.
     *
     * @param source el vértice origen.
     * @param goal   restricción que indica que no quedan más vértices por analizar.
     * @param target el vértice destino.
     * @return el beneficio de los productos de los p`roductos que aún no se han analizado.
     */
    public static Double heuristic(Ex3Problem source, Predicate<Ex3Problem> goal, Ex3Problem target) {
        return (Objects.equals(source.id(), DataEx3.getNumProductos())) ? 0. :
                IntStream.range(source.id(), DataEx3.getNumProductos()).boxed()
                        .mapToDouble(i -> DataEx3.beneficioProductos(i, source.tiempoProduccionRestante(), source.tiempoManualRestante()))
                        .sum();
    }

    public static Double cota(Ex3Problem v, Integer a) {
        return heuristic(v.neighbor(a), null, null) + DataEx3.getIngresos(v.id()) * a;
    }
}
