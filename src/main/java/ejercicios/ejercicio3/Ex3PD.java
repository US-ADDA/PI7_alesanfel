package main.java.ejercicios.ejercicio3;

import us.lsi.common.List2;
import us.lsi.common.Map2;
import us.lsi.common.String2;

import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class Ex3PD {

    static Integer maxValue = Integer.MIN_VALUE;
    static Ex3Problem start;
    static Map<Ex3Problem, SpEx3> memory;

    public static void start() {
        maxValue = Integer.MIN_VALUE;
        start = Ex3Problem.initialVertex();
        memory = Map2.empty();
        search(start, 0, memory);
        Ex3PD.solucion();
    }

    public static SpEx3 search(Ex3Problem vertex, Integer accumulateValue, Map<Ex3Problem, SpEx3> memory) {
        SpEx3 res;
        if (memory.containsKey(vertex))
            res = memory.get(vertex);
        else if (Ex3Problem.goal().test(vertex)) {
            res = SpEx3.of(null, 0);
            memory.put(vertex, res);
            if (accumulateValue > maxValue) maxValue = accumulateValue;
        } else {
            List<SpEx3> soluciones = List2.empty();
            for (Integer a : vertex.actions()) {
                double cota = accumulateValue * 1.0 + Ex3Heuristic.cota(vertex, a);
                if (cota <= maxValue) continue;
                SpEx3 s = search(vertex.neighbor(a), (accumulateValue + vertex.weight(a)), memory);
                if (s != null) {
                    SpEx3 sp = SpEx3.of(a, (s.weight() + vertex.weight(a)));
                    soluciones.add(sp);
                }
            }
            res = soluciones.stream().max(Comparator.naturalOrder()).orElse(null);
            if (res != null)
                memory.put(vertex, res);
        }
        return res;
    }

    public static SolutionEx3 solucion() {
        List<Integer> acciones = List2.empty();
        Ex3Problem v = start;
        SpEx3 s = Ex3PD.memory.get(v);
        while (s.a() != null) {
            acciones.add(s.a());
            v = v.neighbor(s.a());
            s = Ex3PD.memory.get(v);
        }
        return SolutionEx3.of(Ex3PD.start, acciones);
    }

    public static void main(String[] args) {
        Locale.setDefault(new Locale("en", "US"));
        System.out.println("#### Algoritmo PD ####");
        for (int i = 1; i < 3; i++) {
            DataEx3.initialData("data/PI7Ej3DatosEntrada" + i + ".txt");
            System.out.println(String2.linea());
            start();
            System.out.println("-> Para PI7Ej3DatosEntrada" + i + ".txt");
            System.out.println(Ex3PD.solucion());
            System.out.println(String2.linea() + "\n");
        }
    }

    public record SpEx3(Integer a, Integer weight) implements Comparable<SpEx3> {

        public static SpEx3 of(Integer a, Integer weight) {
            return new SpEx3(a, weight);
        }

        @Override
        public int compareTo(SpEx3 sp) {
            return this.weight.compareTo(sp.weight);
        }
    }
}
