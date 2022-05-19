package main.java.ejercicios.ejercicio4;

import us.lsi.common.List2;
import us.lsi.common.Map2;
import us.lsi.common.String2;

import java.util.*;

public class Ex4PD {

    static Integer maxValue = Integer.MIN_VALUE;
    static Ex4Problem start;
    static Map<Ex4Problem, SpEx4> memory;

    public static void start() {
        maxValue = Integer.MIN_VALUE;
        start = Ex4Problem.initialVertex();
        memory = Map2.empty();
        search(start, 0, memory);
        Ex4PD.solucion();
    }

    public static SpEx4 search(Ex4Problem vertex, Integer accumulateValue, Map<Ex4Problem, SpEx4> memory) {
        SpEx4 res;
        if (memory.containsKey(vertex))
            res = memory.get(vertex);
        else if (Ex4Problem.goal().test(vertex)) {
            res = SpEx4.of(null, 0);
            memory.put(vertex, res);
            if (accumulateValue > maxValue) maxValue = accumulateValue;
        } else {
            List<SpEx4> soluciones = List2.empty();
            for (Integer a : vertex.actions()) {
                double cota = accumulateValue * 1.0 + Ex4Heuristic.cota(vertex, a);
                if (cota <= maxValue) continue;
                SpEx4 s = search(vertex.neighbor(a), accumulateValue + vertex.weight(a), memory);
                if (s != null) {
                    SpEx4 sp = SpEx4.of(a, s.weight() + vertex.weight(a));
                    soluciones.add(sp);
                }
            }
            res = soluciones.stream().max(Comparator.naturalOrder()).orElse(null);
            if (res != null) memory.put(vertex, res);
        }
        return res;
    }

    public static SolutionEx4 solucion() {
        List<Integer> acciones = new ArrayList<>();
        Ex4Problem v = Ex4PD.start;
        SpEx4 s = Ex4PD.memory.get(v);
        while (s.a() != null) {
            acciones.add(s.a());
            v = v.neighbor(s.a());
            s = Ex4PD.memory.get(v);
        }
        return SolutionEx4.of(Ex4PD.start, acciones);
    }

    public static void main(String[] args) {
        Locale.setDefault(new Locale("en", "US"));
        System.out.println("#### Algoritmo PD ####");
        for (var i = 1; i < 3; i++) {
            DataEx4.initialData("data/PI7Ej4DatosEntrada" + i + ".txt");
            System.out.println(String2.linea());
            start();
            System.out.println("-> Para PI7Ej4DatosEntrada" + i + ".txt");
            System.out.println(Ex4PD.solucion());
            System.out.println(String2.linea() + "\n");
        }
    }

    public record SpEx4(Integer a, Integer weight) implements Comparable<SpEx4> {

        public static SpEx4 of(Integer a, Integer weight) {
            return new SpEx4(a, weight);
        }

        @Override
        public int compareTo(SpEx4 sp) {
            return this.weight.compareTo(sp.weight);
        }
    }
}
