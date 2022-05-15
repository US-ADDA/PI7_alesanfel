package main.java.ejercicios.ejercicio3;

import us.lsi.common.Files2;

import java.util.ArrayList;
import java.util.List;

/**
 * Los datos necesarios para resolver el ejercicio 3.
 */
public class DataEjercicio3 {

    private static List<Componente> componentes;
    private static List<Producto> productos;
    private static Integer totalProduccion;
    private static Integer totalManual;

    /**
     * Cargar los datos de un fichero.
     *
     * @param path la ruta del fichero.
     */
    public static void initialData(String path) {
        componentes = new ArrayList<>();
        productos = new ArrayList<>();
        for (var linea : Files2.linesFromFile(path)) {
            if (linea.contains("T_prod =") && !linea.contains("//"))
                totalProduccion = Integer.parseInt(linea.split("=")[1].trim());
            else if (linea.contains("T_manual =") && !linea.contains("//"))
                totalManual = Integer.parseInt(linea.split("=")[1].trim());
            else if (linea.contains("C") && !linea.contains("->") && !linea.contains("//"))
                componentes.add(Componente.parse(linea));
            else if (linea.contains("P") && !linea.contains("//"))
                productos.add(Producto.parse(linea));
        }
    }

    // <- MÉTODOS PARA PRODUCTOS -> //

    /**
     * Obtiene los ingresos para un producto.
     *
     * @param i el índice correspondiente al producto en la lista {@code productos}.
     * @return los ingresos que produce el producto.
     */
    public static Integer getIngresos(Integer i) {
        return productos.get(i).precio();
    }

    /**
     * Obtiene el número máximo de unidades que se puede producir de un producto.
     *
     * @param i el índice correspondiente al producto en la lista {@code productos}.
     * @return el número máximo de unidades.
     */
    public static Integer getMaxUnidades(Integer i) {
        return productos.get(i).maxUnidades();
    }

    /**
     * Obtiene el tiempo total de elaboración manual necesario para fabricar el producto,
     *
     * @param i el índice correspondiente al producto en la lista {@code productos}
     * @return el tiempo total de elaboración manual.
     */
    public static Integer getTiempoTotalManualProducto(Integer i) {
        return getProducto(i).componentes().entrySet().stream()
                .mapToInt(entry -> getTiempoComponenteEnManual(entry.getKey()) * entry.getValue())
                .sum();
    }

    /**
     * Obtiene el tiempo total de producción necesario para fabricar el producto,
     *
     * @param i el índice correspondiente al producto en la lista {@code productos}.
     * @return el tiempo total de producción.
     */
    public static Integer getTiempoTotalProduccionProducto(Integer i) {
        return getProducto(i).componentes().entrySet().stream()
                .mapToInt(entry -> getTiempoComponenteEnProduccion(entry.getKey()) * entry.getValue())
                .sum();
    }

    /**
     * Obtiene el número de unidades que podemos producir de un producto.
     *
     * @param i                        el índice correspondiente al producto en la lista {@code productos}.
     * @param tiempoProduccionRestante el tiempo para la fase de producción que aún tenemos.
     * @param tiempoManualRestante     el tiempo para la fase de elaboración manual que aún tenemos.
     * @return número de unidades de un producto.
     */
    public static Integer getRatioUnidades(Integer i, Integer tiempoProduccionRestante, Integer tiempoManualRestante) {
        return Math.min(DataEjercicio3.getMaxUnidades(i),
                Math.min(tiempoProduccionRestante / DataEjercicio3.getTiempoTotalProduccionProducto(i),
                        tiempoManualRestante / DataEjercicio3.getTiempoTotalManualProducto(i)));
    }

    /**
     * Obtenemos el beneficio que podemos obtener del número de unidades que se pueden fabricar.
     *
     * @param i                        el índice correspondiente al producto en la lista {@code productos}.
     * @param tiempoProduccionRestante el tiempo para la fase de producción que aún tenemos.
     * @param tiempoManualRestante     el tiempo para la fase de elaboración manual que aún tenemos.
     * @return beneficio de un producto.
     */
    public static Integer beneficioProductos(Integer i, Integer tiempoProduccionRestante, Integer tiempoManualRestante) {
        return DataEjercicio3.getProducto(i).precio() * DataEjercicio3.getRatioUnidades(i, tiempoProduccionRestante, tiempoManualRestante);
    }

    /**
     * Obtiene una instancia del tipo {@link Producto}.
     *
     * @param i el índice correspondiente al producto en la lista {@code productos}.
     * @return una instancia del tipo {@link Producto}.
     */
    public static Producto getProducto(Integer i) {
        return productos.get(i);
    }

    /**
     * Obtiene el número de productos que disponemos.
     *
     * @return el número de productos que disponemos.
     */
    public static Integer getNumProductos() {
        return productos.size();
    }


    // <- MÉTODOS PARA COMPONENTES -> //

    /**
     * Obtiene el tiempo que necesita el componente para la fase de producción.
     *
     * @param j el índice correspondiente al componente en la lista {@code componentes}.
     * @return tiempo necesario para el componente en la fase de producción.
     */
    private static Integer getTiempoComponenteEnProduccion(Integer j) {
        return componentes.get(j).tiempoProduccion();
    }

    /**
     * Obtiene el tiempo que necesita el componente para la fase manual.
     *
     * @param j el índice correspondiente al componente en la lista {@code componentes}.
     * @return tiempo necesario para el componente en la fase manual.
     */
    private static Integer getTiempoComponenteEnManual(Integer j) {
        return componentes.get(j).tiempoManual();
    }

    // <- OTROS MÉTODOS -> //

    /**
     * Obtiene el tiempo máximo en la fase de producción.
     *
     * @return el tiempo máximo en la fase de producción.
     */
    public static Integer getMaxTiempoEnProduccion() {
        return totalProduccion;
    }

    /**
     * Obtiene el tiempo máximo en la fase manual.
     *
     * @return el tiempo máximo en la fase manual.
     */
    public static Integer getMaxTiempoEnManual() {
        return totalManual;
    }

    private DataEjercicio3() {
    }
}
