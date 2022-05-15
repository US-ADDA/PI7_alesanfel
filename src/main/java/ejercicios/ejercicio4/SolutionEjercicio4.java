package main.java.ejercicios.ejercicio4;

import org.jgrapht.GraphPath;
import us.lsi.common.List2;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;

public class SolutionEjercicio4 {

    private final Map<Contenedor, List<Elemento>> elementosPorContenedor;

    private SolutionEjercicio4(ProblemEjercicio4 start, List<Integer> actions) {
        elementosPorContenedor = new HashMap<>();
        ProblemEjercicio4 p = start;
        for (int i = 0; i < actions.size(); i++) {
            Integer action = actions.get(i);
            if (action < DataEjercicio4.getNumContenedores()) {
                Elemento value = DataEjercicio4.getElemento(i);
                Contenedor key = DataEjercicio4.getContenedor(action);
                if (elementosPorContenedor.containsKey(key))
                    elementosPorContenedor.get(key).add(value);
                else
                    elementosPorContenedor.put(key, List2.of(value));
            }
            p = p.neighbor(action);
        }
    }

    public static SolutionEjercicio4 of(ProblemEjercicio4 start, List<Integer> acciones) {
        return new SolutionEjercicio4(start, acciones);
    }

    @Override
    public String toString() {
        var cadenaContenedores = elementosPorContenedor.entrySet().stream()
                .map(entry -> entry.getKey() + ": " + entry.getValue())
                .reduce("", (ac, nx) -> String.format("%s%s%n", ac, nx));
        return String.format("Reparto obtenido:%n%sNúmero elementos: %s", cadenaContenedores, contenedoresLLenos());
    }

    public Integer contenedoresLLenos() {
        Integer c = 0;
        for (Entry<Contenedor, List<Elemento>> entry : elementosPorContenedor.entrySet()) {
            Integer consumido = entry.getValue().stream().mapToInt(Elemento::tamano).sum();
            if (Objects.equals(entry.getKey().capacidad(), consumido))
                c++;
        }
        return c;
    }
}
