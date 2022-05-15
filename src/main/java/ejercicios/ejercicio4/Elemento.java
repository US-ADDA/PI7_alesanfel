package main.java.ejercicios.ejercicio4;

import us.lsi.common.List2;

import java.util.List;

/**
 * El tipo correspondiente a un elemento que puede ser almacenado en un contenedor.
 */
public record Elemento(String id, Integer tamano, List<String> posiblesContenedores) {

    /**
     * Método de factoría de la clase {@code Elemento}.
     *
     * @param id                   la clave primaria.
     * @param tamano               el espacio que ocupa el objeto en un contenedor.
     * @param posiblesContenedores el tipo de los contenedores en los que puede ser almacenado.
     * @return una instancia del tipo {@code Elemento}.
     */
    public static Elemento of(String id, Integer tamano, List<String> posiblesContenedores) {
        return new Elemento(id, tamano, posiblesContenedores);
    }

    /**
     * Método para parsear un contenedor siguiendo el siguiente criterio:
     * <ul>{code id}: {@code capacidad}; {@code posiblesContenedores}</ul>
     * Si hay más de un elemento en alguno de los campos, se separan por comas.
     *
     * @param linea la línea que va a ser parseada.
     * @return una instancia del tipo {@code Elemento}.
     */
    public static Elemento parse(String linea) {
        String[] infoElemento = linea.split(":")[1].split(";");
        String id = linea.split(": ")[0].trim();
        Integer tamano = Integer.parseInt(infoElemento[0].trim());
        List<String> posiblesContenedores = List2.parse(infoElemento[1], ",", String::trim);
        return of(id, tamano, posiblesContenedores);
    }

    @Override
    public String toString() {
        return String.format("%s: %s; %s", id, tamano, posiblesContenedores);
    }
}
