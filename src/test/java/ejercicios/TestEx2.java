package test.java.ejercicios;

import main.java.ejercicios.ejercicio2.DataEx2;
import main.java.ejercicios.ejercicio2.Ex2Heuristic;
import main.java.ejercicios.ejercicio2.Ex2Problem;
import main.java.ejercicios.ejercicio2.SolutionEx2;
import main.java.tool.AStar;
import main.java.tool.BackTracking;
import main.java.tool.DynamicProgramming;

import java.util.Comparator;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

public class TestEx2 {


    public static void main(String[] args) {
        Consumer<String> initialData = DataEx2::initialData;
        Supplier<Ex2Problem> initialVertex = Ex2Problem::initialVertex;
        Function<List<Integer>, SolutionEx2> solution = SolutionEx2::of;
        Comparator<SolutionEx2> cmp = Comparator.comparing(SolutionEx2::getValoracionTotal);
        String[] path = {"data/PI7Ej2DatosEntrada1.txt", "data/PI7Ej2DatosEntrada2.txt"};
        BackTracking.create(initialData, initialVertex, new Ex2Heuristic(), solution, cmp, path);
        DynamicProgramming.create(initialData, initialVertex, new Ex2Heuristic(), solution, path);
        AStar.create(initialData, initialVertex, new Ex2Heuristic(), solution, path); // Si devuelve que no hay posibles candidatos, es debido a que ninguna de las posibles soluciones cubre todas las cualidades por A*.
    }
}
