package main.java.ejercicios.ejercicio3;

import us.lsi.common.List2;

import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.stream.IntStream;

public record ProblemEjercicio3(Integer indice, Integer tiempoProduccionRestante,
                                Integer tiempoManualRestante) {

    public static ProblemEjercicio3 initialVertex() {
        return of(0, DataEjercicio3.getMaxTiempoEnProduccion(), DataEjercicio3.getMaxTiempoEnManual());
    }

    public static ProblemEjercicio3 of(Integer indice, Integer tiempoProduccionRestante, Integer tiempoManualRestante) {
        return new ProblemEjercicio3(indice, tiempoProduccionRestante, tiempoManualRestante);
    }

    public static Predicate<ProblemEjercicio3> goal() {
        return v -> Objects.equals(v.indice, DataEjercicio3.getNumProductos());
    }

    public List<Integer> actions() {
        return (indice >= DataEjercicio3.getNumProductos()) ?
                List2.empty() :
                IntStream.rangeClosed(0, DataEjercicio3.getRatioUnidades(indice, tiempoProduccionRestante, tiempoManualRestante))
                        .boxed().toList();
    }

    public ProblemEjercicio3 neighbor(Integer a) {
        return of(indice + 1,
                tiempoProduccionRestante - DataEjercicio3.getTiempoTotalProduccionProducto(indice) * a,
                tiempoManualRestante - DataEjercicio3.getTiempoTotalManualProducto(indice) * a);
    }
}
