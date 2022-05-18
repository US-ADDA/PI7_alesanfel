package main.java.ejercicios.ejercicio2;

import us.lsi.common.List2;

import java.util.List;
import java.util.Objects;

public record ProblemEjercicio2(Integer indice,
                                List<Integer> candidatosSeleccionados,
                                List<String> cualidadesACubrir) {

    public static ProblemEjercicio2 of(Integer indice, List<Integer> candidatosSeleccionados, List<String> cualidadesACubrir) {
        return new ProblemEjercicio2(indice, candidatosSeleccionados, cualidadesACubrir);
    }

    public List<Integer> actions() {
        // Si estamos en el último candidato, no se puede realizar ninguna acción.
        if (Objects.equals(indice, DataEjercicio2.getNumCandidatos()))
            return List2.empty();
        // No se pueden contratar candidatos incompatibles.
        for (var i : candidatosSeleccionados) {
            if (Boolean.TRUE.equals(DataEjercicio2.esIncompatible(i, indice)))
                return List.of(0);
        }
        // No se puede superar el presupuesto.
        return DataEjercicio2.getSueldo(indice) <= DataEjercicio2.getPresupuestoRestante(candidatosSeleccionados) ?
                List.of(0, 1) :
                List.of(0);
    }

    public ProblemEjercicio2 neighbor(Integer a) {
        List<Integer> auxCandidatosSeleccionados = List2.copy(candidatosSeleccionados);
        List<String> auxCualidadesACubrir = List2.copy(cualidadesACubrir);
        // Comprobamos que el candidato ha sido contratado.
        if (a == 1) {
            auxCandidatosSeleccionados.add(indice);
            auxCualidadesACubrir.removeAll(DataEjercicio2.getCualidadesCandidato(indice));
        }

        return ProblemEjercicio2.of(indice + 1, auxCandidatosSeleccionados, auxCualidadesACubrir);
    }
}
