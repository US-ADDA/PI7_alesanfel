package main.java.ejercicios.ejercicio2;

import us.lsi.common.List2;

import java.util.List;

public class SolutionEx2 {

    private final List<Candidato> candidatos;
    private Double valoracionMedia, valoracionTotal, gasto;

    public SolutionEx2(Ex2Problem start, List<Integer> actions) {
        candidatos = List2.empty();
        Ex2Problem v = start;
        valoracionMedia = 0.;
        valoracionTotal = 0.;
        gasto = 0.;
        for (int i = 0; i < actions.size(); i++) {
            int action = actions.get(i);
            if (action == 1) {
                Candidato candidato = DataEx2.getCandidato(i);
                candidatos.add(candidato);
                valoracionTotal += candidato.valoracion();
                gasto += candidato.sueldo();
            }
            v = v.neighbor(action);
        }
        valoracionMedia = valoracionTotal / candidatos.size();
    }

    public static SolutionEx2 of(Ex2Problem start, List<Integer> actions) {
        return new SolutionEx2(start, actions);
    }

    public Double getValoracionTotal() {
        return valoracionTotal;
    }

    @Override
    public String toString() {
        String cadenaCandidatos = candidatos.stream().map(Candidato::toString).reduce("", (ac, nx) -> String.format("%s%s%n", ac, nx));
        return String.format("Candidatos Seleccionados:%n%sValoración total: %.1f; Gasto: %.1f; V. media: %.1f", cadenaCandidatos, valoracionTotal, gasto, valoracionMedia);
    }
}
