package main.java.ejercicios.ejercicio1;

/**
 * El tipo correspondiente a una memoria que permite almacenar ficheros.
 */
public record Memoria(String id, Integer capacidad, Integer tamanoMaximo) {

    /**
     * Método de factoría de la clase {@code Memoria}.
     *
     * @param id           la clave primaria.
     * @param capacidad    la capacidad máxima que puede almacenar la memoria.
     * @param tamanoMaximo el espacio máximo que puede llegar a ocupar un fichero en una memoria.
     * @return una instancia del tipo {@code Memoria}.
     */
    public static Memoria of(String id, Integer capacidad, Integer tamanoMaximo) {
        return new Memoria(id, capacidad, tamanoMaximo);
    }

    /**
     * Método para parsear una memoria siguiendo el siguiente criterio:
     * <ul>{@code id}: capacidad={@code capacidad}; tam_max={@code tamanoMaximo};</ul>
     *
     * @param linea la línea que va a ser parseada.
     * @return una instancia del tipo {@code Memoria}.
     */
    public static Memoria parse(String linea) {
        String[] infoMemoria = linea.split(":")[1].split(";");
        String id = linea.split(":")[0];
        Integer capacidad = Integer.parseInt(infoMemoria[0].split("=")[1].trim());
        Integer tamanoMaximo = Integer.parseInt(infoMemoria[1].split("=")[1].trim());
        return of(id, capacidad, tamanoMaximo);
    }

    @Override
    public String toString() {
        return String.format("%s: %s; %s", id, capacidad, tamanoMaximo);
    }
}
