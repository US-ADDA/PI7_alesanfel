package main.java.ejercicios.ejercicio2;

import us.lsi.common.List2;
import us.lsi.common.Set2;
import us.lsi.common.String2;

import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Set;

public class Ex2BT {

    static Ex2Problem start;
    static StateEx2 estado;
    static Integer maxValue;
    static Set<SolutionEx2> soluciones;

    public static void start() {
        start = Ex2Problem.initialVertex();
        estado = StateEx2.of(start);
        maxValue = Integer.MIN_VALUE;
        soluciones = Set2.empty();
        search();
    }

    public static void search() {
        if (Ex2Problem.goal().test(estado.vertice())) {
            Integer value = estado.valorAcumulado();
            if (value > maxValue && Ex2Problem.constraints().test(estado.vertice())) {
                maxValue = value;
                soluciones.add(estado.solucion());
            }
        } else {
            List<Integer> alternativas = estado.vertice().actions();
            for (Integer a : alternativas) {
                double cota = estado.valorAcumulado() + Ex2Heuristic.cota(estado.vertice(), a);
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
        for (int i = 1; i < 3; i++) {
            DataEx2.initialData("data/PI7Ej2DatosEntrada" + i + ".txt");
            Ex2BT.start();
            System.out.println(String2.linea());
            System.out.println("-> Para PI7Ej2DatosEntrada" + i + ".txt");
            System.out.println(Ex2BT.soluciones.stream().max(Comparator.comparing(SolutionEx2::getValoracionTotal)).orElse(null));
            System.out.println(String2.linea() + "\n");
        }

    }

    public static class StateEx2 {
        private final List<Integer> acciones;
        private final List<Ex2Problem> vertices;
        private Ex2Problem vertice;
        private Integer valorAcumulado;

        private StateEx2(Ex2Problem vertice, List<Integer> acciones,
                         List<Ex2Problem> vertices) {
            this.vertice = vertice;
            this.valorAcumulado = 0;
            this.acciones = acciones;
            this.vertices = vertices;
        }

        public static StateEx2 of(Ex2Problem vertex) {
            List<Ex2Problem> vt = List2.of(vertex);
            return new StateEx2(vertex, List2.empty(), vt);
        }

        void forward(Integer a) {
            acciones.add(a);
            Ex2Problem vcn = vertice().neighbor(a);
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

        SolutionEx2 solucion() {
            return SolutionEx2.of(this.acciones);
        }

        public Ex2Problem vertice() {
            return vertice;
        }

        public Integer valorAcumulado() {
            return valorAcumulado;
        }
    }
}
