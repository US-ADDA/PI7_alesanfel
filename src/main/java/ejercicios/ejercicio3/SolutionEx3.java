package main.java.ejercicios.ejercicio3;

import us.lsi.common.List2;
import us.lsi.common.Pair;

import java.util.List;

public class SolutionEx3 {

    private final List<Pair<Producto, Double>> productos;
    private Double beneficio;

    private SolutionEx3(Ex3Problem start, List<Integer> actions) {
        productos = List2.empty();
        Ex3Problem v = start;
        beneficio = 0.;
        for (int i = 0; i < actions.size(); i++) {
            int action = actions.get(i);
            if (action > 0) {
                Producto producto = DataEx3.getProducto(i);
                productos.add(Pair.of(producto, action * 1.0));
                beneficio += producto.precio() * action;
            }
            v = v.neighbor(action);
        }
    }

    public static SolutionEx3 of(Ex3Problem start, List<Integer> actions) {
        return new SolutionEx3(start, actions);
    }

    @Override
    public String toString() {
        String cadenaProductos = productos.stream()
                .map(pair -> pair.first().id() + ": " + Math.round(pair.second()) + " unidades")
                .reduce("", (ac, nx) -> String.format("%s%s%n", ac, nx));
        return String.format("Productos seleccionados:%n%sBeneficio: %s", cadenaProductos, beneficio);
    }
}