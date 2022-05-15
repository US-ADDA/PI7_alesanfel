package main.java.ejercicios.ejercicio2;

import us.lsi.common.Files2;
import us.lsi.common.List2;

import java.util.List;

/**
 * Los datos necesarios para resolver el ejercicio 2.
 */
public class DataEjercicio2 {

    private static List<Candidato> candidatos;
    private static List<String> cualidades;
    private static Integer presupuestoMaximo;

    /**
     * Carga los datos de un fichero.
     *
     * @param path la ruta del fichero.
     */
    public static void initialData(String path) {
        candidatos = List2.empty();
        cualidades = List2.empty();
        for (String linea : Files2.linesFromFile(path)) {
            if (linea.contains("Cualidades Deseadas: "))
                cualidades = List2.parse(linea.split(":")[1], ",", String::trim);
            else if (linea.contains("Presupuesto Máximo: "))
                presupuestoMaximo = Integer.parseInt(linea.split(":")[1].trim());
            else
                candidatos.add(Candidato.parse(linea));
        }
    }

    // <- MÉTODOS PARA CANDIDATOS -> //

    /**
     * Obtiene la valoración para un candidato.
     *
     * @param i el índice correspondiente al candidato en la lista {@code candidatos}.
     * @return la valoración de un candidato.
     */
    public static Integer getValoracion(Integer i) {
        return candidatos.get(i).valoracion();
    }

    /**
     * Obtiene el sueldo mínimo de un candidato.
     *
     * @param i el índice correspondiente al candidato en la lista {@code candidatos}.
     * @return el sueldo mínimo del candidato.
     */
    public static Double getSueldo(Integer i) {
        return candidatos.get(i).sueldo();
    }

    /**
     * Obtiene las cualidades de un candidato.
     *
     * @param i el índice correspondiente al candidato en la lista {@code candidatos}.
     * @return las cualidades de un candidato.
     */
    public static List<String> getCualidadesCandidato(Integer i) {
        return candidatos.get(i).cualidades();
    }

    /**
     * Devuelve {@code true} si el primer candidato es incompatible con el segundo candidato, en caso contrario, devuelve {@code false}.
     *
     * @param i el índice correspondiente al primer candidato en la lista {@code candidatos}.
     * @param k el índice correspondiente al segundo candidato en la lista {@code candidatos}.
     * @return un {@link Boolean} indicando si son o no compatibles.
     */
    public static Boolean esIncompatible(Integer i, Integer k) {
        return candidatos.get(i).incompatibilidadesPorCandidato().contains(candidatos.get(k).id());
    }

    /**
     * Obtiene una instancia del tipo {@link Candidato}.
     *
     * @param i el índice correspondiente al candidato en la lista {@code candidatos}.
     * @return una instancia del tipo {@link Candidato}.
     */
    public static Candidato getCandidato(Integer i) {
        return candidatos.get(i);
    }

    /**
     * Obtiene el número de candidatos que disponemos.
     *
     * @return el número de memorias que disponemos.
     */
    public static Integer getNumCandidatos() {
        return candidatos.size();
    }


    // <- MÉTODOS PARA CUALIDADES -> //

    /**
     * Obtiene las cualidades que se desean que tengan los empleados.
     *
     * @return las cualidades deseadas por la empresa.
     */
    public static List<String> getCualidadesDeseadas() {
        return cualidades;
    }


    // <- MÉTODOS PARA PRESUPUESTO -> //

    /**
     * Obtiene el presupuesto que aún no se ha gastado por el sueldo de los candidatos contratados.
     *
     * @param candidatos los candidatos que han sido contratados.
     * @return el presupuesto restante.
     */
    public static Double getPresupuestoRestante(List<Integer> candidatos) {
        return candidatos.stream().mapToDouble(DataEjercicio2::getSueldo)
                .reduce(presupuestoMaximo, (ac, nx) -> ac - nx);

    }

    private DataEjercicio2() {
    }
}
