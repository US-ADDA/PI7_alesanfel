package test.java.ejercicios;

import main.java.ejercicios.ejercicio1.DataEx1;
import main.java.ejercicios.ejercicio1.Ex1Heuristic;
import main.java.ejercicios.ejercicio1.Ex1Problem;
import main.java.ejercicios.ejercicio1.SolutionEx1;
import main.java.tool.AStar;
import main.java.tool.BackTracking;
import main.java.tool.DynamicProgramming;

import java.util.Comparator;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

public class TestEx1 {

    public static void main(String[] args) {
        Consumer<String> initialData = DataEx1::initialData;
        Supplier<Ex1Problem> initialVertex = Ex1Problem::initialVertex;
        Function<List<Integer>, SolutionEx1> solution = SolutionEx1::of;
        Comparator<SolutionEx1> cmp = Comparator.comparing(SolutionEx1::getNumFicheros);
        String[] path = {"data/PI7Ej1DatosEntrada1.txt", "data/PI7Ej1DatosEntrada2.txt"};
        // BackTracking.create(initialData, initialVertex, new Ex1Heuristic(), solution, cmp, path);
        // DynamicProgramming.create(initialData, initialVertex, new Ex1Heuristic(), solution, path);
        AStar.create(initialData, initialVertex, new Ex1Heuristic(), solution, path);
    }
}
