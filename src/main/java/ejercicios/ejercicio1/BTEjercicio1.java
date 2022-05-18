package main.java.ejercicios.ejercicio1;

import us.lsi.common.List2;
import us.lsi.common.Set2;

import java.util.*;

public class BTEjercicio1 {

    public static class StateEjercicio1 {
        private ProblemEjercicio1 vertice;
        private Integer valorAcumulado;
        private final List<Integer> acciones;
        private final List<ProblemEjercicio1> vertices;

        private StateEjercicio1(ProblemEjercicio1 vertice, Integer valorAcumulado, List<Integer> acciones,
                                List<ProblemEjercicio1> vertices) {
            this.vertice = vertice;
            this.valorAcumulado = valorAcumulado;
            this.acciones = acciones;
            this.vertices = vertices;
        }

        public static StateEjercicio1 of(ProblemEjercicio1 vertex) {
            List<ProblemEjercicio1> vt = List2.of(vertex);
            return new StateEjercicio1(vertex, 0, List2.empty(), vt);
        }

        void forward(Integer a) {
            acciones.add(a);
            ProblemEjercicio1 vcn = vertice().neighbor(a);
            vertices.add(vcn);
            valorAcumulado += ((a < DataEjercicio1.getNumMemoria()) ? 1 : 0);
            vertice = vcn;
        }

        void back(Integer a) {
            acciones.remove(acciones.size() - 1);
            vertices.remove(vertices.size() - 1);
            vertice = vertices.get(vertices.size() - 1);
            valorAcumulado -= ((a < DataEjercicio1.getNumMemoria()) ? 1 : 0);
        }

        SolutionEjercicio1 solucion() {
            return SolutionEjercicio1.of(acciones);
        }

        public ProblemEjercicio1 vertice() {
            return vertice;
        }

        public Integer valorAcumulado() {
            return valorAcumulado;
        }

    }

    static ProblemEjercicio1 start;
    static StateEjercicio1 estado;
    static Integer maxValue;
    static Set<SolutionEjercicio1> soluciones;

    public static void btm(List<Integer> capacidadRestante) {
        start = ProblemEjercicio1.of(0, capacidadRestante);
        estado = StateEjercicio1.of(start);
        maxValue = Integer.MIN_VALUE;
        soluciones = Set2.empty();
        btm();
    }

    public static void btm() {
        if (Objects.equals(estado.vertice().indice(), DataEjercicio1.getNumFichero())) {
            Integer value = estado.valorAcumulado();
            if (value > maxValue) {
                maxValue = value;
                soluciones.add(BTEjercicio1.estado.solucion());
            }
        } else {
            List<Integer> alternativas = estado.vertice().actions();
            for (Integer a : alternativas) {
                double cota = estado.valorAcumulado() + HeuristicEjercicio1.cota(estado.vertice(), a);
                if (cota > maxValue) {
                    estado.forward(a);
                    btm();
                    estado.back(a);
                }
            }
        }
    }

    public static void main(String[] args) {
        Locale.setDefault(new Locale("en", "US"));
        DataEjercicio1.initialData("data/PI7Ej1DatosEntrada1.txt");
        BTEjercicio1.btm(DataEjercicio1.getMemorias().stream().map(Memoria::capacidad).toList());
        System.out.println(soluciones.stream().max(Comparator.comparing(SolutionEjercicio1::getNumFicheros)).orElse(null));
    }


}
