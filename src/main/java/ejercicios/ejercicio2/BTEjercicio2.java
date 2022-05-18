package main.java.ejercicios.ejercicio2;

import us.lsi.common.List2;

import java.util.*;

public class BTEjercicio2 {

    public static class StateEjercicio2 {
        private ProblemEjercicio2 vertice;
        private Integer valorAcumulado;
        private List<Integer> acciones;
        private List<ProblemEjercicio2> vertices;

        private StateEjercicio2(ProblemEjercicio2 vertice, Integer valorAcumulado, List<Integer> acciones,
                                List<ProblemEjercicio2> vertices) {
            super();
            this.vertice = vertice;
            this.valorAcumulado = valorAcumulado;
            this.acciones = acciones;
            this.vertices = vertices;
        }

        public static StateEjercicio2 of(ProblemEjercicio2 vertex) {
            List<ProblemEjercicio2> vt = new ArrayList<>();
            vt.add(vertex);
            return new StateEjercicio2(vertex,0,new ArrayList<>(),vt);
        }

        void forward(Integer a) {
            acciones.add(a);
            ProblemEjercicio2 vcn = vertice().neighbor(a);
            vertices.add(vcn);
            valorAcumulado = valorAcumulado() + DataEjercicio2.getValoracion(vertice.indice())*a; // Peso de la arista
            vertice = vcn;
        }

        void back(Integer a) {
            acciones.remove(acciones.size()-1);
            vertices.remove(vertices.size()-1);
            vertice = vertices.get(vertices.size()-1);
            valorAcumulado = valorAcumulado() - DataEjercicio2.getValoracion(vertice.indice())*a;
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

    static ProblemEjercicio2 start;
    static StateEjercicio2 estado;
    static Integer maxValue;
    static Set<SolutionEjercicio2> soluciones;

    public static void btm( List<Integer> candidatosSeleccionados, List<String> cualidadesACubrir) {
        start = ProblemEjercicio2.of(0,candidatosSeleccionados, cualidadesACubrir);
        estado = StateEjercicio2.of(start);
        maxValue = Integer.MIN_VALUE;
        soluciones = new HashSet<>();
        btm();
    }

    public static void btm() {
        if(Objects.equals(estado.vertice().indice(), DataEjercicio2.getNumCandidatos())) {
            Integer value = estado.valorAcumulado();
            if(value > BTEjercicio2.maxValue && estado.vertice.cualidadesACubrir().isEmpty()) {
                maxValue = value;
                soluciones.add(BTEjercicio2.estado.solucion());
            }
        } else {
            List<Integer> alternativas = BTEjercicio2.estado.vertice().actions();
            for(Integer a:alternativas) {
                double cota = BTEjercicio2.estado.valorAcumulado() + HeuristicEjercicio2.cota(estado.vertice(), a);
                if(cota > BTEjercicio2.maxValue) {
                    BTEjercicio2.estado.forward(a);
                    btm();
                    BTEjercicio2.estado.back(a);
                }
            }
        }
    }

    public static void main(String[] args) {
        Locale.setDefault(new Locale("en", "US"));
        DataEjercicio2.initialData("data/PI7Ej2DatosEntrada1.txt");
        BTEjercicio2.btm(List2.empty(), DataEjercicio2.getCualidadesDeseadas());
        System.out.println(BTEjercicio2.soluciones.stream().max(Comparator.comparing(SolutionEjercicio2::getValoracionTotal)).orElse(null));
    }
}
