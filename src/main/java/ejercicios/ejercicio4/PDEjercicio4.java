package main.java.ejercicios.ejercicio4;


import main.java.ejercicios.ejercicio3.PDEjercicio3;
import us.lsi.common.List2;

import java.util.*;
import java.util.stream.IntStream;

public class PDEjercicio4 {

    public record Sp4(Integer a, Integer weight) implements Comparable<Sp4> {

        public static Sp4 of(Integer a, Integer weight) {
            return new Sp4(a, weight);
        }

        @Override
        public int compareTo(Sp4 sp) {
            return this.weight.compareTo(sp.weight);
        }
    }

    public static Integer maxValue = Integer.MIN_VALUE;
    public static ProblemEjercicio4 start;
    public static Map<ProblemEjercicio4, Sp4> memory;

    public static SolutionEjercicio4 pd(List<Integer> capacidadRestante) {
        maxValue = Integer.MIN_VALUE;
        start = ProblemEjercicio4.of(0,capacidadRestante);
        memory = new HashMap<>();
        pd(start,0,memory);
        return PDEjercicio4.solucion();
    }

    public static Sp4 pd(ProblemEjercicio4 vertex, Integer accumulateValue, Map<ProblemEjercicio4, Sp4> memory) {
        Sp4 res;
        if(memory.containsKey(vertex)) {
            res = memory.get(vertex);
        } else if(Objects.equals(vertex.indice(), DataEjercicio4.getNumElementos())) {
            res = Sp4.of(null,0);
            if (ProblemEjercicio4.constraint().test(vertex)) {
                memory.put(vertex,res);
                if(accumulateValue > maxValue) maxValue = accumulateValue;
            }

        } else {
            List<Sp4> soluciones = List2.empty();
            for(Integer a:vertex.actions()) {
                double cota = accumulateValue*1.0 + HeuristicEjercicio4.cota(vertex,a);
                if(cota <= PDEjercicio3.maxValue) continue;
                Sp4 s = pd(vertex.neighbor(a),vertex.weight(),memory);
                if(s!=null) {
                    Sp4 sp = Sp4.of(a,s.weight());
                    soluciones.add(sp);
                }
            }
            res = soluciones.stream().max(Comparator.naturalOrder()).orElse(null);
            if(res!=null) memory.put(vertex,res);
        }
        return res;
    }

    public static SolutionEjercicio4 solucion(){
        List<Integer> acciones = new ArrayList<>();
        ProblemEjercicio4 v = PDEjercicio4.start;
        Sp4 s = PDEjercicio4.memory.get(v);
        while(s.a() != null) {
            acciones.add(s.a());
            v = v.neighbor(s.a());
            s = PDEjercicio4.memory.get(v);
        }
        return SolutionEjercicio4.of(PDEjercicio4.start,acciones);
    }

    public static void main(String[] args) {
        Locale.setDefault(new Locale("en", "US"));
        DataEjercicio4.initialData("data/PI7Ej4DatosEntrada1.txt");
        pd(IntStream.range(0, DataEjercicio4.getNumContenedores()).boxed().map(DataEjercicio4::getCapacidadContenedor).toList());
        System.out.println(PDEjercicio4.solucion());
    }
}
