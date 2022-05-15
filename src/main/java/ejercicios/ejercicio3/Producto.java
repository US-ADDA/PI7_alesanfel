package main.java.ejercicios.ejercicio3;

import java.util.HashMap;
import java.util.Map;

/**
 * El tipo correspondiente a un producto formado con distintos componentes.
 */
public record Producto(String id, Integer precio, Map<Integer, Integer> componentes, Integer maxUnidades) {

    /**
     * Método de factoría de la clase {@code Producto}.
     *
     * @param id          la clave primaria.
     * @param precio      el precio del producto.
     * @param componentes los componentes necesarios para elaborar el producto.
     * @param maxUnidades la cantidad máxima de unidades que se puede llegar a producir del producto.
     * @return una instancia del tipo {@code Producto}.
     */
    public static Producto of(String id, Integer precio, Map<Integer, Integer> componentes, Integer maxUnidades) {
        return new Producto(id, precio, componentes, maxUnidades);
    }

    /**
     * Método para parsear un candidato siguiendo el siguiente criterio:
     * <ul>{@code id} -> precio={@code precio}; comp=({@code idComponente}: {@code numComponente}); max_u={@code maxUnidades}</ul>
     * Si hay más de un elemento en alguno de los campos, se separan por comas.
     *
     * @param linea la línea que va a ser parseada.
     * @return una instancia del tipo {@code Producto}.
     */
    public static Producto parse(String linea) {
        String[] infoProducto = linea.split(">")[1].split(";");
        Map<Integer, Integer> map = new HashMap<>();
        String[] componentesEnProducto = infoProducto[1].split("=")[1].split(",");
        for (String numComponente : componentesEnProducto) {
            String[] data = numComponente.split(":");
            String auxKey = data[0].replaceAll("\\(C", "");
            while (auxKey.charAt(0) == '0')
                auxKey = auxKey.substring(1);
            int key = Integer.parseInt(auxKey);
            Integer value = Integer.parseInt(data[1].replaceAll("\\)", ""));
            map.put(key - 1, value);
        }
        return Producto.of(linea.split(">")[0].replaceAll(" -", ""), Integer.parseInt(infoProducto[0].split("=")[1].trim()),
                map, Integer.parseInt(infoProducto[2].split("=")[1].trim()));
    }
}
