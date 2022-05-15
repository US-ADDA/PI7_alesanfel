package main.java.ejercicios.ejercicio3;

/**
 * El tipo correspondiente a un componente que, si se ensambla, da lugar a un producto.
 */
public record Componente(String id, Integer tiempoProduccion, Integer tiempoManual) {

    /**
     * Método de factoría de la clase {@code Componente}.
     *
     * @param id               la clave primaria.
     * @param tiempoProduccion tiempo de producción del componente.
     * @param tiempoManual     tiempo manual del componente.
     * @return una instancia del tipo {@code Componente}.
     */
    public static Componente of(String id, Integer tiempoProduccion, Integer tiempoManual) {
        return new Componente(id, tiempoProduccion, tiempoManual);
    }

    /**
     * Método para parsear un componente siguiendo el siguiente criterio:
     * <ul>{@code id}: prod={@code tiempoProduccion}; elab={@code tiempoManual};</ul>
     *
     * @param linea la línea que va a ser parseada.
     * @return una instancia del tipo {@code Componente}.
     */
    public static Componente parse(String linea) {
        String[] infoComponente = linea.split(":")[1].split(";");
        String id = linea.split(":")[0];
        Integer tiempoProduccion = Integer.parseInt(infoComponente[0].split("=")[1].trim());
        Integer tiempoManual = Integer.parseInt(infoComponente[1].split("=")[1].trim());
        return of(id, tiempoProduccion, tiempoManual);
    }
}
