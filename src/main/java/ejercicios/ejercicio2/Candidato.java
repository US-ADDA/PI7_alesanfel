package main.java.ejercicios.ejercicio2;

import us.lsi.common.List2;

import java.util.List;

/**
 * El tipo correspondiente a los candidatos para un trabajo.
 */
public record Candidato(String id, List<String> cualidades,
                        List<String> incompatibilidadesPorCandidato, Double sueldo, Integer valoracion) {

    /**
     * Método de factoría de la clase {@code Candidato}.
     *
     * @param id                             la clave primaria.
     * @param cualidades                     las cualidades que posse el candidato.
     * @param incompatibilidadesPorCandidato contiene las claves primarias de los candidatos con los cuales es incompatible.
     * @param sueldo                         el sueldo mínimo que va a ganar el candidato.
     * @param valoracion                     la valoración del candidato.
     * @return una instancia del tipo {@code Candidato}.
     */
    public static Candidato of(String id, List<String> cualidades,
                               List<String> incompatibilidadesPorCandidato, Double sueldo, Integer valoracion) {
        return new Candidato(id, cualidades, incompatibilidadesPorCandidato, sueldo, valoracion);
    }

    /**
     * Método para parsear un candidato siguiendo el siguiente criterio:
     * <ul>{@code id}: {@code cualidadesPorCandidato}; {@code sueldo}; {@code valoracion}; {@code incompatibilidadesPorCandidato}</ul>
     * Si hay más de un elemento en alguno de los campos, se separan por comas.
     *
     * @param linea la línea que va a ser parseada.
     * @return una instancia del tipo {@code Candidato}.
     */
    public static Candidato parse(String linea) {
        String[] data = linea.split(":")[1].split(";");
        String id = linea.split(":")[0];
        List<String> cualidadesPorCandidato = List2.parse(data[0], ",", String::trim);
        List<String> incompatibilidadesPorCandidato = List2.parse(data[3], ",", String::trim);
        Double sueldo = Double.parseDouble(data[1].trim());
        Integer valoracion = Integer.parseInt(data[2].trim());
        return Candidato.of(id, cualidadesPorCandidato, incompatibilidadesPorCandidato, sueldo, valoracion);
    }

    @Override
    public String toString() {
        return String.format("%s: %s; %s; %s; %s", id, cualidades, sueldo, valoracion, incompatibilidadesPorCandidato);
    }
}
