package main.java.ejercicios.ejercicio1;

import java.util.*;

public class Ejercicio1BT {

    public static class StateEjercicio1 {
        private ProblemEjercicio1 vertice;
        private Integer valorAcumulado;
        private List<Integer> acciones;
        private List<ProblemEjercicio1> vertices;

        private StateEjercicio1(ProblemEjercicio1 vertice, Integer valorAcumulado, List<Integer> acciones,
                                List<ProblemEjercicio1> vertices) {
            super();
            this.vertice = vertice;
            this.valorAcumulado = valorAcumulado;
            this.acciones = acciones;
            this.vertices = vertices;
        }

        public static StateEjercicio1 of(ProblemEjercicio1 vertex) {
            List<ProblemEjercicio1> vt = new ArrayList<>();
            vt.add(vertex);
            return new StateEjercicio1(vertex,0,new ArrayList<>(),vt);
        }

        void forward(Integer a) {
            this.acciones.add(a);
            ProblemEjercicio1 vcn = this.vertice().neighbor(a);
            this.vertices.add(vcn);
            this.valorAcumulado = this.valorAcumulado() /*+ a * DataEjercicio1.valor(this.vertice().index())*/;
            this.vertice = vcn;
        }

        void back(Integer a) {
            this.acciones.remove(this.acciones.size()-1);
            this.vertices.remove(this.vertices.size()-1);
            this.vertice = this.vertices.get(this.vertices.size()-1);
            this.valorAcumulado = this.valorAcumulado() /*- a * DataEjercicio1.valor(this.vertice.index())*/;
        }

        SolutionEjercicio1 solucion() {
            return SolutionEjercicio1.of(Ejercicio1BT.start,this.acciones);
        }

        public ProblemEjercicio1  vertice() {
            return vertice;
        }

        public Integer valorAcumulado() {
            return valorAcumulado;
        }

    }

    public static ProblemEjercicio1 start;
    public static StateEjercicio1 estado;
    public static Integer maxValue;
    public static Set<SolutionEjercicio1> soluciones;

    public static void btm(List<Integer> capacidadRestante) {
        Ejercicio1BT.start = ProblemEjercicio1.of(0,capacidadRestante);
        Ejercicio1BT.estado = StateEjercicio1.of(start);
        Ejercicio1BT.maxValue = Integer.MIN_VALUE;
        Ejercicio1BT.soluciones = new HashSet<>();
        btm();
    }

    public static void btm(List<Integer> capacidadRestante, Integer maxValue, SolutionEjercicio1 s) {
        Ejercicio1BT.start = ProblemEjercicio1.of(0, capacidadRestante);
        Ejercicio1BT.estado = StateEjercicio1.of(start);
        Ejercicio1BT.maxValue = maxValue;
        Ejercicio1BT.soluciones = new HashSet<>();
        Ejercicio1BT.soluciones.add(s);
        btm();
    }

    public static void btm() {
        if(Objects.equals(Ejercicio1BT.estado.vertice().indice(), DataEjercicio1.getNumFichero())) {
            Integer value = estado.valorAcumulado();
            if(value > Ejercicio1BT.maxValue) {
                Ejercicio1BT.maxValue = value;
                Ejercicio1BT.soluciones.add(Ejercicio1BT.estado.solucion());
            }
        } else {
            List<Integer> alternativas = Ejercicio1BT.estado.vertice().actions();
            for(Integer a:alternativas) {
                Double cota = Ejercicio1BT.estado.valorAcumulado()*1.0/*+Heuristica.cota(Ejercicio1BT.estado.vertice(),a)*/;
                if(cota < Ejercicio1BT.maxValue) continue;
                Ejercicio1BT.estado.forward(a);
                btm();
                Ejercicio1BT.estado.back(a);
            }
        }
    }

    public static void main(String[] args) {
        Locale.setDefault(new Locale("en", "US"));
        DataEjercicio1.initialData("ficheros/objetosMochila.txt");
        //ProblemEjercicio1 v1 = ProblemEjercicio1.of(0, DataEjercicio1.getMemorias().stream().map(Memoria::capacidad).toList());
        //SolucionMochila s = Heuristica.solucionVoraz(v1);
        //long startTime = System.nanoTime();
        Ejercicio1BT.btm(DataEjercicio1.getMemorias().stream().map(Memoria::capacidad).toList());
        //long endTime = System.nanoTime() - startTime;
        //System.out.println("1 = "+endTime);
        //System.out.println(Ejercicio1BT.soluciones);
        //startTime = System.nanoTime();
        //Ejercicio1BT.btm(78,s.valor(),s);
        //long endTime2 = System.nanoTime() - startTime;
        //System.out.println("2 = "+endTime2);
        //System.out.println("2 = "+1.*endTime2/endTime);
        System.out.println(Ejercicio1BT.soluciones);
    }


}
