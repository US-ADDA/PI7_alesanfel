package main.java.ejercicios.ejercicio4;

/**
 * El tipo correspondiente a un contenedor el cual permite almacenar elementos.
 */
public record Contenedor(String id, Integer capacidad, String tipo) {

    /**
     * Método de factoría de la clase {@code Contenedor}.
     *
     * @param id        la clave primaria.
     * @param capacidad la capacidad máxima del contenedor.
     * @param tipo      el tipo al cual pertenece el contenedor.
     * @return una instancia del tipo {@code Contenedor}.
     */
    public static Contenedor of(String id, Integer capacidad, String tipo) {
        return new Contenedor(id, capacidad, tipo);
    }

    /**
     * Método para parsear un contenedor siguiendo el siguiente criterio:
     * <ul>{@code id}: capacidad={@code capacidad}; tipo={@code tipo};</ul>
     *
     * @param linea la línea que va a ser parseada.
     * @return una instancia del tipo {@code Candidato}.
     */
    public static Contenedor parse(String linea) {
        String[] infoContenedor = linea.split(":")[1].split(";");
        String id = linea.split(":")[0].trim();
        Integer capacidad = Integer.parseInt(infoContenedor[0].split("=")[1]);
        String tipo = infoContenedor[1].split("=")[1];
        return of(id, capacidad, tipo);
    }

    @Override
    public String toString() {
        return String.format("%s: %s; %s", id, capacidad, tipo);
    }
}
