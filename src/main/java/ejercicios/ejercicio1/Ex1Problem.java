package main.java.ejercicios.ejercicio1;

import us.lsi.common.List2;

import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public record Ex1Problem(Integer id, List<Integer> capacidadRestante) {

    public static Ex1Problem of(Integer indice, List<Integer> capacidadRestante) {
        return new Ex1Problem(indice, capacidadRestante);
    }

    // Datos del problema.
    public static Ex1Problem initialVertex() {
        return of(0, DataEx1.getMemorias().stream().map(Memoria::capacidad).toList());
    }

    public static Predicate<Ex1Problem> goal() {
        return v -> Objects.equals(v.id(), DataEx1.getNumFichero());
    }

    public List<Integer> actions() {
        // Si estamos en el último fichero, no se puede realizar ninguna acción.
        if (Objects.equals(id, DataEx1.getNumFichero()))
            return List2.empty();
        List<Integer> acciones = IntStream.range(0, capacidadRestante.size())
                // Debe de haber espacio en esa memoria y no superar el tamaño máximo permitido.
                .filter(j -> DataEx1.ficheroEnMemoria(id, j, capacidadRestante))
                .boxed().collect(Collectors.toList());
        // El fichero puede no ser almacenado en una memoria.
        acciones.add(DataEx1.getNumMemoria());
        return acciones;
    }

    public Ex1Problem neighbor(Integer a) {
        var auxCapacidadRestante = List2.copy(capacidadRestante);
        // Comprobamos que el fichero se ha colocado en una memoria y si lo está, disminuimos la capacidad de la memoria correspondiente.
        if (!Objects.equals(a, DataEx1.getNumMemoria()))
            auxCapacidadRestante.set(a, capacidadRestante.get(a) - DataEx1.getCapacidadFichero(id));
        return Ex1Problem.of(id + 1, auxCapacidadRestante);
    }

    public Integer weight(Integer a) {
        // Si el fichero se encuentra en una memoria 1, en caso contrario 0.
        return Objects.equals(a, DataEx1.getNumMemoria()) ? 0 : 1;
    }
}
