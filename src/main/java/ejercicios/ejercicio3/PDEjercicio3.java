package main.java.ejercicios.ejercicio3;

import us.lsi.common.List2;
import us.lsi.common.Map2;

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
        maxValue = Integer.MIN_VALUE;
        start = ProblemEjercicio3.of(0,tiempoProduccionRestante,tiempoManualRestante);
        memory = Map2.empty();
        pd(start,0,memory);
        return PDEjercicio3.solucion();
    }

    public static Spm pd(ProblemEjercicio3 vertex, Integer accumulateValue, Map<ProblemEjercicio3,Spm> memory) {
        Spm res;
        if(memory.containsKey(vertex)) {
            res = memory.get(vertex);
        } else if(Objects.equals(vertex.indice(), DataEjercicio3.getNumProductos())) {
            res = Spm.of(null,0);
            memory.put(vertex,res);
            if(accumulateValue > PDEjercicio3.maxValue) PDEjercicio3.maxValue = accumulateValue;
        } else {
            List<Spm> soluciones = List2.empty();
            for(Integer a:vertex.actions()) {
                Double cota = accumulateValue*1.0 + HeuristicEjercicio3.cota(vertex,a);
                if(cota <= PDEjercicio3.maxValue) continue;
                Spm s = pd(vertex.neighbor(a),accumulateValue+DataEjercicio3.getIngresos(vertex.indice()) * a,memory);
                if(s!=null) {
                    Spm sp = Spm.of(a,s.weight()+a*DataEjercicio3.getIngresos(vertex.indice()));
                    soluciones.add(sp);
                }
            }
            res = soluciones.stream().max(Comparator.naturalOrder()).orElse(null);
            if(res!=null) memory.put(vertex,res);
        }
        return res;
    }

    public static SolutionEjercicio3 solucion(){
        List<Integer> acciones = new ArrayList<>();
        ProblemEjercicio3 v = start;
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
        DataEjercicio3.initialData("data/PI7Ej3DatosEntrada1.txt");
        pd(DataEjercicio3.getMaxTiempoEnProduccion(), DataEjercicio3.getMaxTiempoEnManual());
        System.out.println(PDEjercicio3.solucion());
    }
}
