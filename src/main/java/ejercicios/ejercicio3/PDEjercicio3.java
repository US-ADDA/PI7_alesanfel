package main.java.ejercicios.ejercicio3;

import java.util.*;

public class PDEjercicio3 {

    public static record Spm(Integer a,Integer weight) implements Comparable<Spm> {

        public static Spm of(Integer a, Integer weight) {
            return new Spm(a, weight);
        }

        @Override
        public int compareTo(Spm sp) {
            return this.weight.compareTo(sp.weight);
        }
    }

    public static Integer maxValue = Integer.MIN_VALUE;
    public static ProblemEjercicio3 start;
    public static Map<ProblemEjercicio3,Spm> memory;

    public static SolutionEjercicio3 pd(Integer tiempoProduccionRestante, Integer tiempoManualRestante) {
        PDEjercicio3.maxValue = Integer.MIN_VALUE;
        PDEjercicio3.start = ProblemEjercicio3.of(0,tiempoProduccionRestante,tiempoManualRestante);
        PDEjercicio3.memory = new HashMap<>();
        pd(start,0,memory);
        return PDEjercicio3.solucion();
    }

    public static SolutionEjercicio3 pd(Integer tiempoProduccionRestante, Integer tiempoManualRestante, Integer maxValue, SolutionEjercicio3 s) {
        PDEjercicio3.maxValue = maxValue;
        PDEjercicio3.start = ProblemEjercicio3.of(0,tiempoProduccionRestante,tiempoManualRestante);
        PDEjercicio3.memory = new HashMap<>();
        pd(start,0,memory);
        if(PDEjercicio3.memory.get(start) == null) return s;
        else return PDEjercicio3.solucion();
    }

    public static Spm pd(ProblemEjercicio3 vertex, Integer accumulateValue, Map<ProblemEjercicio3,Spm> memory) {
        Spm r;
        if(memory.containsKey(vertex)) {
            r = memory.get(vertex);
        } else if(Objects.equals(vertex.indice(), DataEjercicio3.getNumProductos())) {
            r = Spm.of(null,0);
            memory.put(vertex,r);
            if(accumulateValue > PDEjercicio3.maxValue) PDEjercicio3.maxValue = accumulateValue;
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

    public static SolutionEjercicio3 solucion(){
        List<Integer> acciones = new ArrayList<>();
        ProblemEjercicio3 v = PDEjercicio3.start;
        Spm s = PDEjercicio3.memory.get(v);
        while(s.a() != null) {
            acciones.add(s.a());
            v = v.neighbor(s.a());
            s = PDEjercicio3.memory.get(v);
        }
        return SolutionEjercicio3.of(PDEjercicio3.start,acciones);
    }

    public static void main(String[] args) {
        Locale.setDefault(new Locale("en", "US"));
        DataEjercicio3.initialData("ficheros/objetosMochila.txt");
        // DatosMochila.capacidadInicial = 78;
        ProblemEjercicio3 v1 = ProblemEjercicio3.of(0, DataEjercicio3.getMaxTiempoEnProduccion(), DataEjercicio3.getMaxTiempoEnManual());
        //SolucionMochila s = Heuristica.solucionVoraz(v1);
        //PDEjercicio3.pd(78);
        //System.out.println(PDEjercicio3.solucion());
        //PDEjercicio3.pd(78,s.valor(),s);
        System.out.println(PDEjercicio3.solucion());
    }
}
