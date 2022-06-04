package test.java.ejercicios;

import main.java.ejercicios.ejercicio3.DataEx3;
import main.java.ejercicios.ejercicio3.Ex3Heuristic;
import main.java.ejercicios.ejercicio3.Ex3Problem;
import main.java.ejercicios.ejercicio3.SolutionEx3;
import main.java.tool.AStar;
import main.java.tool.BackTracking;
import main.java.tool.DynamicProgramming;

import java.util.Comparator;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

public class TestEx3 {

    public static void main(String[] args) {
        Consumer<String> initialData = DataEx3::initialData;
        Supplier<Ex3Problem> initialVertex = Ex3Problem::initialVertex;
        Function<List<Integer>, SolutionEx3> solution = SolutionEx3::of;
        String[] path = {"data/PI7Ej3DatosEntrada1.txt", "data/PI7Ej3DatosEntrada2.txt"};
        BackTracking.create(initialData, initialVertex, new Ex3Heuristic(), solution, Comparator.comparing(SolutionEx3::getBeneficio), path);
        DynamicProgramming.create(initialData, initialVertex, new Ex3Heuristic(), solution, path);
        AStar.create(initialData, initialVertex, new Ex3Heuristic(), solution, path);
    }
}
