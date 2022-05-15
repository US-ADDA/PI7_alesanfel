package main.java.ejercicios.ejercicio3;

import us.lsi.common.List2;
import us.lsi.common.Pair;

import java.util.List;

public class SolutionEjercicio3 {

    private final List<Pair<Producto, Double>> productos;
    private Double beneficio;

    private SolutionEjercicio3(ProblemEjercicio3 start, List<Integer> actions) {
        productos = List2.empty();
        ProblemEjercicio3 v = start;
        beneficio = 0.;
        for (int i = 0; i < actions.size(); i++) {
            int action = actions.get(i);
            if (action > 0) {
                Producto producto = DataEjercicio3.getProducto(i);
                productos.add(Pair.of(producto, action * 1.0));
                beneficio += producto.precio() * action;
            }
            v = v.neighbor(action);
        }
    }

    public static SolutionEjercicio3 of(ProblemEjercicio3 start, List<Integer> actions) {
        return new SolutionEjercicio3(start, actions);
    }

    @Override
    public String toString() {
        String cadenaProductos = productos.stream()
                .map(pair -> pair.first().id() + ": " + Math.round(pair.second()) + " unidades")
                .reduce("", (ac, nx) -> String.format("%s%s%n", ac, nx));
        return String.format("Productos seleccionados:%n%sBeneficio: %s", cadenaProductos, beneficio);
    }
}