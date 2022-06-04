package main.java.ejercicios.ejercicio2;

import main.java.tool.Problem;
import us.lsi.common.List2;

import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;

public record Ex2Problem(Integer id,
                         List<Integer> candidatosSeleccionados,
                         List<String> cualidadesACubrir) implements Problem {

    public static Ex2Problem of(Integer indice, List<Integer> candidatosSeleccionados, List<String> cualidadesACubrir) {
        return new Ex2Problem(indice, candidatosSeleccionados, cualidadesACubrir);
    }

    // Datos para el problema.
    public static Ex2Problem initialVertex() {
        return of(0, List2.empty(), DataEx2.getCualidadesDeseadas());
    }

    public Predicate<Ex2Problem> goal() {
        return v -> Objects.equals(v.id, DataEx2.getNumCandidatos());
    }

    public Boolean constraints() {
        // La solución correcta debe de cubrir las cualidades deseadas.
        return cualidadesACubrir.isEmpty();
    }

    public List<Integer> actions() {
        // Si estamos en el último candidato, no se puede realizar ninguna acción.
        if (Objects.equals(id, DataEx2.getNumCandidatos()))
            return List2.empty();
        // No se pueden contratar candidatos incompatibles.
        for (var i : candidatosSeleccionados) {
            if (Boolean.TRUE.equals(DataEx2.esIncompatible(i, id)))
                return List.of(0);
        }
        // No se puede superar el presupuesto.
        return DataEx2.getSueldo(id) <= DataEx2.getPresupuestoRestante(candidatosSeleccionados) ?
                List.of(0, 1) :
                List.of(0);
    }

    public Ex2Problem neighbor(Integer a) {
        List<Integer> auxCandidatosSeleccionados = List2.copy(candidatosSeleccionados);
        List<String> auxCualidadesACubrir = List2.copy(cualidadesACubrir);
        // Comprobamos que el candidato ha sido contratado.
        if (a == 1) {
            auxCandidatosSeleccionados.add(id);
            auxCualidadesACubrir.removeAll(DataEx2.getCualidadesCandidato(id));
        }

        return Ex2Problem.of(id + 1, auxCandidatosSeleccionados, auxCualidadesACubrir);
    }

    public Integer weight(Integer a) {
        // Contamos la valoración si y solo si el empleado ha sido contratado (entre 0 y 5).
        return a * DataEx2.getValoracion(id);
    }
}
