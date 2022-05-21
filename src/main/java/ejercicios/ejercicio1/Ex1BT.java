package main.java.ejercicios.ejercicio1;

import us.lsi.common.List2;
import us.lsi.common.Set2;
import us.lsi.common.String2;

import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Set;

public class Ex1BT {

    static Ex1Problem start;
    static StateEx1 estado;
    static Integer maxValue;
    static Set<SolutionEx1> soluciones;

    public static void start() {
        start = Ex1Problem.initialVertex();
        estado = StateEx1.of(start);
        maxValue = Integer.MIN_VALUE;
        soluciones = Set2.empty();
        search();
    }

    public static void search() {
        if (Ex1Problem.goal().test(estado.vertice())) {
            Integer value = estado.valorAcumulado();
            if (value > maxValue) {
                maxValue = value;
                soluciones.add(estado.solucion());
            }
        } else {
            List<Integer> alternativas = estado.vertice().actions();
            for (Integer a : alternativas) {
                double cota = estado.valorAcumulado() + Ex1Heuristic.cota(estado.vertice(), a);
                if (cota > maxValue) {
                    estado.forward(a);
                    search();
                    estado.back(a);
                }
            }
        }
    }

    public static void main(String[] args) {
        Locale.setDefault(new Locale("en", "US"));
        System.out.println("#### Algoritmo BT ####");
        for (var i = 1; i < 3; i++) {
            DataEx1.initialData("data/PI7Ej1DatosEntrada" + i + ".txt");
            Ex1BT.start();
            System.out.println(String2.linea());
            System.out.println("-> Para PI7Ej1DatosEntrada" + i + ".txt");
            System.out.println(soluciones.stream().max(Comparator.comparing(SolutionEx1::getNumFicheros)).orElse(null));
            System.out.println(String2.linea() + "\n");
        }
    }

    public static class StateEx1 {
        private final List<Integer> acciones;
        private final List<Ex1Problem> vertices;
        private Ex1Problem vertice;
        private Integer valorAcumulado;

        private StateEx1(Ex1Problem vertice, List<Integer> acciones, List<Ex1Problem> vertices) {
            this.vertice = vertice;
            this.valorAcumulado = 0;
            this.acciones = acciones;
            this.vertices = vertices;
        }

        public static StateEx1 of(Ex1Problem vertex) {
            List<Ex1Problem> vt = List2.of(vertex);
            return new StateEx1(vertex, List2.empty(), vt);
        }

        void forward(Integer a) {
            acciones.add(a);
            Ex1Problem vcn = vertice().neighbor(a);
            vertices.add(vcn);
            valorAcumulado += vertice.weight(a);
            vertice = vcn;
        }

        void back(Integer a) {
            acciones.remove(acciones.size() - 1);
            vertices.remove(vertices.size() - 1);
            vertice = vertices.get(vertices.size() - 1);
            valorAcumulado -= vertice.weight(a);
        }

        SolutionEx1 solucion() {
            return SolutionEx1.of(acciones);
        }

        public Ex1Problem vertice() {
            return vertice;
        }

        public Integer valorAcumulado() {
            return valorAcumulado;
        }
    }
}
