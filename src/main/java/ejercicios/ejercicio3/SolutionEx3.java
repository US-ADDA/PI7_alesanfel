package main.java.ejercicios.ejercicio3;

import main.java.tool.Solution;
import us.lsi.common.List2;
import us.lsi.common.Pair;

import java.util.List;

public class SolutionEx3 implements Solution {

    private final List<Pair<Producto, Double>> productos;
    private Double beneficio;

    private SolutionEx3(List<Integer> actions) {
        productos = List2.empty();
        beneficio = 0.;
        for (int i = 0; i < actions.size(); i++) {
            int action = actions.get(i);
            if (action > 0) {
                Producto producto = DataEx3.getProducto(i);
                productos.add(Pair.of(producto, action * 1.0));
                beneficio += producto.precio() * action;
            }
        }
    }

    public static SolutionEx3 of(List<Integer> actions) {
        return new SolutionEx3(actions);
    }

    public Double getBeneficio() {
        return beneficio;
    }

    @Override
    public String toString() {
        String cadenaProductos = productos.stream()
                .map(pair -> pair.first().id() + ": " + Math.round(pair.second()) + " unidades")
                .reduce("", (ac, nx) -> String.format("%s%s%n", ac, nx));
        return String.format("Productos seleccionados:%n%sBeneficio: %s", cadenaProductos, beneficio);
    }
}
