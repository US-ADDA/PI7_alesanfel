package main.java.ejercicios.ejercicio1;

import us.lsi.common.List2;
import us.lsi.graphs.virtual.VirtualVertex;

import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public record ProblemEjercicio1(Integer indice, List<Integer> capacidadRestante)  {

    public static ProblemEjercicio1 of(Integer indice, List<Integer> capacidadRestante) {
        return new ProblemEjercicio1(indice, capacidadRestante);
    }

    public List<Integer> actions() {
        // Si estamos en el último fichero, no se puede realizar ninguna acción.
        if (Objects.equals(indice, DataEjercicio1.getNumFichero()))
            return List2.empty();
        List<Integer> acciones = IntStream.range(0, capacidadRestante.size())
                // Debe de haber espacio en esa memoria y no superar el tamaño máximo permitido.
                .filter(j -> DataEjercicio1.ficheroEnMemoria(indice, j, capacidadRestante))
                .boxed().collect(Collectors.toList());
        // El fichero puede no ser almacenado en una memoria.
        acciones.add(DataEjercicio1.getNumMemoria());
        return acciones;
    }

    public ProblemEjercicio1 neighbor(Integer a) {
        var auxCapacidadRestante = List2.copy(capacidadRestante);
        // Comprobamos que el fichero se ha colocado en una memoria y si lo está, disminuimos la capacidad de la memoria correspondiente.
        if (!Objects.equals(a, DataEjercicio1.getNumMemoria()))
            auxCapacidadRestante.set(a, capacidadRestante.get(a) - DataEjercicio1.getCapacidadFichero(indice));
        return ProblemEjercicio1.of(indice + 1, auxCapacidadRestante);
    }
}
