package main.java.ejercicios.ejercicio4;


import main.java.ejercicios.ejercicio3.DataEjercicio3;
import main.java.ejercicios.ejercicio3.PDEjercicio3;

import java.util.*;
import java.util.stream.IntStream;

public class PDEjercicio4 {

    public static record Spm(Integer a,Integer weight) implements Comparable<Spm> {

        public static Spm of(Integer a, Integer weight) {
            return new PDEjercicio4.Spm(a, weight);
        }

        @Override
        public int compareTo(Spm sp) {
            return this.weight.compareTo(sp.weight);
        }
    }

    public static Integer maxValue = Integer.MIN_VALUE;
    public static ProblemEjercicio4 start;
    public static Map<ProblemEjercicio4, Spm> memory;

    public static SolutionEjercicio4 pd(List<Integer> capacidadRestante) {
        maxValue = Integer.MIN_VALUE;
        PDEjercicio4.start = ProblemEjercicio4.of(0,capacidadRestante);
        PDEjercicio4.memory = new HashMap<>();
        pd(start,0,memory);
        return PDEjercicio4.solucion();
    }

    public static PDEjercicio4.Spm pd(ProblemEjercicio4 vertex, Integer accumulateValue, Map<ProblemEjercicio4, Spm> memory) {
        Spm r;
        if(memory.containsKey(vertex)) {
            r = memory.get(vertex);
        } else if(Objects.equals(vertex.indice(), DataEjercicio4.getNumElementos())) {
            r = Spm.of(null,0);
            if (ProblemEjercicio4.constraint().test(vertex)) {
                memory.put(vertex,r);
                if(accumulateValue > PDEjercicio4.maxValue) PDEjercicio4.maxValue = accumulateValue;
            }
        } else {
            List<Spm> soluciones = new ArrayList<>();
            for(Integer a:vertex.actions()) {
                double cota = accumulateValue*1.0 + HeuristicEjercicio4.cota(vertex,a);
                if(cota < PDEjercicio3.maxValue) continue;
                Spm s = pd(vertex.neighbor(a),vertex.weight(),memory);
                if(s!=null) {
                    Spm sp = Spm.of(a,s.weight());
                    soluciones.add(sp);
                }
            }
            r = soluciones.stream().max(Comparator.naturalOrder()).orElse(null);
            if(r!=null) memory.put(vertex,r);
        }
        return r;
    }

    public static SolutionEjercicio4 solucion(){
        List<Integer> acciones = new ArrayList<>();
        ProblemEjercicio4 v = PDEjercicio4.start;
        Spm s = PDEjercicio4.memory.get(v);
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
        PDEjercicio4.pd(IntStream.range(0, DataEjercicio4.getNumContenedores()).boxed().map(DataEjercicio4::getCapacidadContenedor).toList());
        System.out.println(PDEjercicio4.solucion());
    }
}
