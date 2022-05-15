package main.java.ejercicios.ejercicio4;


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
        PDEjercicio4.maxValue = Integer.MIN_VALUE;
        PDEjercicio4.start = ProblemEjercicio4.of(0,capacidadRestante);
        PDEjercicio4.memory = new HashMap<>();
        pd(start,0,memory);
        return PDEjercicio4.solucion();
    }

    public static SolutionEjercicio4 pd(List<Integer> capacidadRestante, Integer maxValue, SolutionEjercicio4 s) {
        PDEjercicio4.maxValue = maxValue;
        PDEjercicio4.start = ProblemEjercicio4.of(0,capacidadRestante);
        PDEjercicio4.memory = new HashMap<>();
        pd(start,0,memory);
        if(PDEjercicio4.memory.get(start) == null) return s;
        else return PDEjercicio4.solucion();
    }

    public static PDEjercicio4.Spm pd(ProblemEjercicio4 vertex, Integer accumulateValue, Map<ProblemEjercicio4, Spm> memory) {
        Spm r;
        if(memory.containsKey(vertex)) {
            r = memory.get(vertex);
        } else if(Objects.equals(vertex.indice(), DataEjercicio4.getNumElementos())) {
            r = Spm.of(null,0);
            memory.put(vertex,r);
            if(accumulateValue > PDEjercicio4.maxValue) PDEjercicio4.maxValue = accumulateValue;
        } else {
            List<Spm> soluciones = new ArrayList<>();
            for(Integer a:vertex.actions()) {
                Double cota = accumulateValue*1.0 /*+ Heuristica.cota(vertex,a)*/;
                if(cota < PDEjercicio3.maxValue) continue;
                Spm s = pd(vertex.neighbor(a),accumulateValue/*+a*DatosMochila.valor(vertex.index())*/,memory);
                if(s!=null) {
                    Spm sp = Spm.of(a,s.weight()/*+a*DataEjercicio3.valor(vertex.index())*/);
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
        DataEjercicio4.initialData("ficheros/objetosMochila.txt");
        // DatosMochila.capacidadInicial = 78;
        ProblemEjercicio4 v1 = ProblemEjercicio4.of(0, IntStream.range(0, DataEjercicio4.getNumContenedores()).boxed().map(DataEjercicio4::getCapacidadContenedor).toList());
        //SolucionMochila s = Heuristica.solucionVoraz(v1);
        //PDEjercicio3.pd(78);
        //System.out.println(PDEjercicio3.solucion());
        //PDEjercicio3.pd(78,s.valor(),s);
        System.out.println(PDEjercicio3.solucion());
    }
}
