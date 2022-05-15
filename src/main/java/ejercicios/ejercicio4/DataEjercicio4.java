package main.java.ejercicios.ejercicio4;

import us.lsi.common.Files2;
import us.lsi.common.List2;

import java.util.List;

/**
 * Los datos necesarios para resolver el ejercicio 4.
 */
public class DataEjercicio4 {

    private static List<Elemento> elementos;
    private static List<Contenedor> contenedores;

    /**
     * Carga los datos de un fichero.
     *
     * @param path la ruta del fichero.
     */
    public static void initialData(String path) {
        contenedores = List2.empty();
        elementos = List2.empty();
        for (String linea : Files2.linesFromFile(path)) {
            if (linea.contains("CONT") && !linea.contains("//"))
                contenedores.add(Contenedor.parse(linea));
            else if (linea.contains("E") && !linea.contains("//"))
                elementos.add(Elemento.parse(linea));
        }
    }

    // <- MÉTODOS PARA ELEMENTOS -> //

    /**
     * Obtiene el espacio que ocupa el elemento en un contenedor.
     *
     * @param i el índice correspondiente al elemento en la lista {@code elementos}.
     * @return el tamaño del elemento.
     */
    public static Integer getTamanoElemento(Integer i) {
        return elementos.get(i).tamano();
    }

    /**
     * Obtiene una instancia del tipo {@link Elemento}.
     *
     * @param i el índice correspondiente al elemento en la lista {@code elementos}.
     * @return una instancia del tipo {@link Elemento}.
     */
    public static Elemento getElemento(Integer i) {
        return elementos.get(i);
    }

    /**
     * Obtiene el número de elementos que disponemos.
     *
     * @return el número de memorias que disponemos.
     */
    public static Integer getNumElementos() {
        return elementos.size();
    }

    // <- MÉTODOS PARA CONTENEDORES -> //

    /**
     * Obtiene la capacidad que posee el contenedor.
     *
     * @param j el índice correspondiente al contenedor en la lista {@code contenedores}.
     * @return la capacidad del contenedor.
     */
    public static Integer getCapacidadContenedor(Integer j) {
        return contenedores.get(j).capacidad();
    }

    /**
     * Obtiene una instancia del tipo {@link Contenedor}.
     *
     * @param j el índice correspondiente al contenedor en la lista {@code contenedores}.
     * @return una instancia del tipo {@link Contenedor}.
     */
    public static Contenedor getContenedor(Integer j) {
        return contenedores.get(j);
    }

    /**
     * Obtiene el número de contenedores que disponemos.
     *
     * @return el número de contenedores que disponemos.
     */
    public static Integer getNumContenedores() {
        return contenedores.size();
    }


    // <- MÉTODOS PARA AMBOS -> //

    /**
     * Devuelve {@code true} si el elemento es compatible con el contenedor, en caso contrario, devuelve {@code false}.
     *
     * @param i el índice correspondiente al elemento en la lista {@code elementos}.
     * @param j el índice correspondiente al contenedor en la lista {@code contenedores}.
     * @return {@link Boolean} indicando si el elemento es compatible con el contenedor.
     */
    public static Boolean esCompatible(Integer i, Integer j) {
        return elementos.get(i).posiblesContenedores().contains(contenedores.get(j).tipo());
    }

    /**
     * Devuelve {@code true} si el elemento puede ser almacenado en el contenedor, en caso contrario, devuelve {@code false}.
     *
     * @param i                 el índice correspondiente al elemento en la lista {@code elementos}.
     * @param j                 el índice correspondiente al contenedor en la lista {@code contenedores}.
     * @param capacidadRestante la capacidad libre en cada uno de los contenedores.
     * @return {@link Boolean} indicando si el elemento puede ser almacenado en el contenedor.
     */
    public static Boolean elementoEnContenedor(Integer i, Integer j, List<Integer> capacidadRestante) {
        return capacidadRestante.get(j) >= DataEjercicio4.getTamanoElemento(j) && DataEjercicio4.esCompatible(i, j);
    }

    private DataEjercicio4() {
    }
}
