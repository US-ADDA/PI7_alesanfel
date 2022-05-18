package main.java.ejercicios.ejercicio2;

import us.lsi.common.List2;

import java.util.List;

public class SolutionEjercicio2 {

    private final List<Candidato> candidatos;
    private Double valoracionMedia, valoracionTotal, gasto;

    public SolutionEjercicio2(ProblemEjercicio2 start, List<Integer> actions) {
        candidatos = List2.empty();
        ProblemEjercicio2 v = start;
        valoracionMedia = 0.;
        valoracionTotal = 0.;
        gasto = 0.;
        for (int i = 0; i < actions.size(); i++) {
            int action = actions.get(i);
            if (action == 1) {
                Candidato candidato = DataEjercicio2.getCandidato(i);
                candidatos.add(candidato);
                valoracionTotal += candidato.valoracion();
                gasto += candidato.sueldo();
            }
            v = v.neighbor(action);
        }
        valoracionMedia = valoracionTotal / candidatos.size();
    }

    public Double getValoracionTotal() {
        return valoracionTotal;
    }

    public static SolutionEjercicio2 of(ProblemEjercicio2 start, List<Integer> actions) {
        return new SolutionEjercicio2(start, actions);
    }

    @Override
    public String toString() {
        String cadenaCandidatos = candidatos.stream().map(Candidato::toString).reduce("", (ac, nx) -> String.format("%s%s%n", ac, nx));
        return String.format("Candidatos Seleccionados:%n%sValoración total: %.1f; Gasto: %.1f; V. media: %.1f", cadenaCandidatos, valoracionTotal, gasto, valoracionMedia);
    }
}
