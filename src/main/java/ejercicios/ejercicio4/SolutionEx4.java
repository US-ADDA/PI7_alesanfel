package main.java.ejercicios.ejercicio4;

import main.java.tool.Solution;
import us.lsi.common.List2;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;

public class SolutionEx4 implements Solution {

    private final Map<Contenedor, List<Elemento>> elementosPorContenedor;

    private SolutionEx4(List<Integer> actions) {
        elementosPorContenedor = new HashMap<>();

        for (int i = 0; i < actions.size(); i++) {
            Integer action = actions.get(i);
            if (action < DataEx4.getNumContenedores()) {
                Elemento value = DataEx4.getElemento(i);
                Contenedor key = DataEx4.getContenedor(action);
                if (elementosPorContenedor.containsKey(key))
                    elementosPorContenedor.get(key).add(value);
                else
                    elementosPorContenedor.put(key, List2.of(value));
            }
        }
    }

    public static SolutionEx4 of(List<Integer> acciones) {
        return new SolutionEx4(acciones);
    }

    @Override
    public String toString() {
        var cadenaContenedores = elementosPorContenedor.entrySet().stream()
                .map(entry -> entry.getKey() + ": " + entry.getValue())
                .reduce("", (ac, nx) -> String.format("%s%s%n", ac, nx));
        return String.format("Reparto obtenido:%n%sNúmero elementos: %s", cadenaContenedores, contenedoresLlenos());
    }

    public Integer contenedoresLlenos() {
        Integer c = 0;
        for (Entry<Contenedor, List<Elemento>> entry : elementosPorContenedor.entrySet()) {
            Integer consumido = entry.getValue().stream().mapToInt(Elemento::tamano).sum();
            if (Objects.equals(entry.getKey().capacidad(), consumido))
                c++;
        }
        return c;
    }
}
