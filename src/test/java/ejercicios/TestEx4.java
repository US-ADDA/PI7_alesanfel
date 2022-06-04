package test.java.ejercicios;

import main.java.ejercicios.ejercicio4.DataEx4;
import main.java.ejercicios.ejercicio4.Ex4Heuristic;
import main.java.ejercicios.ejercicio4.Ex4Problem;
import main.java.ejercicios.ejercicio4.SolutionEx4;
import main.java.tool.AStar;
import main.java.tool.BackTracking;
import main.java.tool.DynamicProgramming;

import java.util.Comparator;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

public class TestEx4 {

    public static void main(String[] args) {
        Consumer<String> initialData = DataEx4::initialData;
        Supplier<Ex4Problem> initialVertex = Ex4Problem::initialVertex;
        Function<List<Integer>, SolutionEx4> solution = SolutionEx4::of;
        Comparator<SolutionEx4> cmp = Comparator.comparing(SolutionEx4::contenedoresLlenos);
        String[] path = {"data/PI7Ej4DatosEntrada1.txt", "data/PI7Ej4DatosEntrada2.txt"};
        BackTracking.create(initialData, initialVertex, new Ex4Heuristic(), solution, cmp, path);
        DynamicProgramming.create(initialData, initialVertex, new Ex4Heuristic(), solution, path);
        AStar.create(initialData, initialVertex, new Ex4Heuristic(), solution, path);
    }
}
