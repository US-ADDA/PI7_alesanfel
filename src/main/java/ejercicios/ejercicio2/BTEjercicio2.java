package main.java.ejercicios.ejercicio2;

import main.java.ejercicios.ejercicio1.DataEjercicio1;
import us.lsi.common.List2;

import java.util.*;

public class BTEjercicio2 {

    public static class StateMochila {
        private ProblemEjercicio2 vertice;
        private Integer valorAcumulado;
        private List<Integer> acciones;
        private List<ProblemEjercicio2> vertices;

        private StateMochila(ProblemEjercicio2 vertice, Integer valorAcumulado, List<Integer> acciones,
                             List<ProblemEjercicio2> vertices) {
            super();
            this.vertice = vertice;
            this.valorAcumulado = valorAcumulado;
            this.acciones = acciones;
            this.vertices = vertices;
        }

        public static StateMochila of(ProblemEjercicio2 vertex) {
            List<ProblemEjercicio2> vt = new ArrayList<>();
            vt.add(vertex);
            return new StateMochila(vertex,0,new ArrayList<>(),vt);
        }

        void forward(Integer a) {
            this.acciones.add(a);
            ProblemEjercicio2 vcn = this.vertice().neighbor(a);
            this.vertices.add(vcn);
            this.valorAcumulado = this.valorAcumulado() /*+ a * DatosMochila.valor(this.vertice().index())*/;
            this.vertice = vcn;
        }

        void back(Integer a) {
            this.acciones.remove(this.acciones.size()-1);
            this.vertices.remove(this.vertices.size()-1);
            this.vertice = this.vertices.get(this.vertices.size()-1);
            this.valorAcumulado = this.valorAcumulado() /*- a * DatosMochila.valor(this.vertice.index())*/;
        }

        SolutionEjercicio2 solucion() {
            return SolutionEjercicio2.of(BTEjercicio2.start,this.acciones);
        }

        public ProblemEjercicio2 vertice() {
            return vertice;
        }

        public Integer valorAcumulado() {
            return valorAcumulado;
        }

    }

    public static ProblemEjercicio2 start;
    public static StateMochila estado;
    public static Integer maxValue;
    public static Set<SolutionEjercicio2> soluciones;

    public static void btm( List<Integer> candidatosSeleccionados, List<String> cualidadesACubrir) {
        BTEjercicio2.start = ProblemEjercicio2.of(0,candidatosSeleccionados, cualidadesACubrir);
        BTEjercicio2.estado = StateMochila.of(start);
        BTEjercicio2.maxValue = Integer.MIN_VALUE;
        BTEjercicio2.soluciones = new HashSet<>();
        btm();
    }

    public static void btm(List<Integer> candidatosSeleccionados, List<String> cualidadesACubrir, Integer maxValue, SolutionEjercicio2 s) {
        BTEjercicio2.start = ProblemEjercicio2.of(0,candidatosSeleccionados, cualidadesACubrir);
        BTEjercicio2.estado = StateMochila.of(start);
        BTEjercicio2.maxValue = maxValue;
        BTEjercicio2.soluciones = new HashSet<>();
        BTEjercicio2.soluciones.add(s);
        btm();
    }

    public static void btm() {
        if(Objects.equals(BTEjercicio2.estado.vertice().indice(), DataEjercicio2.getNumCandidatos())) {
            Integer value = estado.valorAcumulado();
            if(value > BTEjercicio2.maxValue) {
                BTEjercicio2.maxValue = value;
                BTEjercicio2.soluciones.add(BTEjercicio2.estado.solucion());
            }
        } else {
            List<Integer> alternativas = BTEjercicio2.estado.vertice().actions();
            for(Integer a:alternativas) {
                Double cota = BTEjercicio2.estado.valorAcumulado()*1.0/*+Heuristica.cota(BTEjercicio2.estado.vertice(),a)*/;
                if(cota < BTEjercicio2.maxValue) continue;
                BTEjercicio2.estado.forward(a);
                btm();
                BTEjercicio2.estado.back(a);
            }
        }
    }

    public static void main(String[] args) {
        Locale.setDefault(new Locale("en", "US"));
        DataEjercicio1.initialData("ficheros/objetosMochila.txt");
        //DatosMochila.capacidadInicial = 78;
        //ProblemEjercicio2 v1 = ProblemEjercicio2.of(0, List2.empty(), DataEjercicio2.getCualidadesDeseadas());
        // SolucionMochila s = Heuristica.solucionVoraz(v1);
        // long startTime = System.nanoTime();
        BTEjercicio2.btm(List2.empty(), DataEjercicio2.getCualidadesDeseadas());
        //long endTime = System.nanoTime() - startTime;
        //System.out.println("1 = "+endTime);
        //System.out.println(BTEjercicio2.soluciones);
        //startTime = System.nanoTime();
        //BTEjercicio2.btm(78,s.valor(),s);
        //long endTime2 = System.nanoTime() - startTime;
        //System.out.println("2 = "+endTime2);
        //System.out.println("2 = "+1.*endTime2/endTime);
        System.out.println(BTEjercicio2.soluciones);
    }
}
