package main.java.ejercicios.ejercicio1;

import us.lsi.common.List2;
import us.lsi.common.Map2;

import java.util.List;
import java.util.Map;

public class SolutionEx1 {

    private final Map<Memoria, List<Fichero>> memorias;
    private Integer numFicheros;

    private SolutionEx1(List<Integer> actions) {
        numFicheros = 0;
        memorias = Map2.empty();
        for (int i = 0; i < actions.size(); i++) {
            Integer action = actions.get(i);
            if (action < DataEx1.getNumMemoria()) {
                numFicheros++;
                Memoria key = DataEx1.getMemoria(action);
                Fichero value = DataEx1.getFichero(i);
                if (memorias.containsKey(key))
                    memorias.get(key).add(value);
                else
                    memorias.put(key, List2.of(value));
            }
        }
    }

    public static SolutionEx1 of(List<Integer> actions) {
        return new SolutionEx1(actions);
    }

    public Integer getNumFicheros() {
        return numFicheros;
    }

    @Override
    public String toString() {
        String cadenaMemorias = memorias.entrySet().stream()
                .map(entry -> entry.getKey() + ": " + entry.getValue())
                .reduce("", (ac, nx) -> String.format("%s%s%n", ac, nx));
        return String.format("Reparto obtenido:%n%sSe almacenaron %s archivos.", cadenaMemorias, numFicheros);
    }
}
