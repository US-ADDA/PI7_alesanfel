package main.java.ejercicios.ejercicio4;

import us.lsi.common.List2;

import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public record Ex4Problem(Integer id, List<Integer> capacidadRestante) {

    public static Ex4Problem of(Integer id, List<Integer> capacidades) {
        return new Ex4Problem(id, capacidades);
    }

    // Datos del problema.
    public static Ex4Problem initialVertex() {
        return Ex4Problem.of(0, IntStream.range(0, DataEx4.getNumContenedores()).boxed().map(DataEx4::getCapacidadContenedor).toList());
    }

    public static Predicate<Ex4Problem> goal() {
        return v -> Objects.equals(v.id(), DataEx4.getNumElementos());
    }

    public List<Integer> actions() {
        // Si estamos en el último elemento, no se puede realizar ninguna acción.
        if (Objects.equals(id, DataEx4.getNumElementos()))
            return List2.empty();


        List<Integer> acciones = IntStream.range(0, DataEx4.getNumContenedores())
                // Para cada elemento y para cada contenedor, sólo se puede ubicar en caso de que esté permitido acorde a sus tipos.
                .filter(contenedor -> DataEx4.elementoEnContenedor(id, contenedor, capacidadRestante))
                .boxed().collect(Collectors.toList());
        // El elemento puede no ser almacenado en un contenedor.
        acciones.add(0, DataEx4.getNumContenedores());
        return acciones;
    }

    public Ex4Problem neighbor(Integer a) {

        List<Integer> auxCapacidadRestante = List2.copy(capacidadRestante);
        // Comprobamos que el elemento se ha colocado en un contenedor y si lo está, disminuimos la capacidad del contenedor correspondiente.
        if (!Objects.equals(a, DataEx4.getNumContenedores()))
            auxCapacidadRestante.set(a, capacidadRestante.get(a) - DataEx4.getTamanoElemento(id));
        return of(id + 1, auxCapacidadRestante);
    }

    public Integer weight(Integer a) {
        // Si hemos logrado llenar un contenedor, es el camino correcto.
        Integer cantidadLlenosSource = DataEx4.getNumeroContenedoresLLenos(capacidadRestante());
        Integer cantidadLlenosTarget = DataEx4.getNumeroContenedoresLLenos(neighbor(a).capacidadRestante());
        return (cantidadLlenosTarget > cantidadLlenosSource) ? 1 : 0;
    }
}
