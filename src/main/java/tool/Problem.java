package main.java.tool;

import java.util.List;
import java.util.function.Predicate;

public interface Problem {


    <A extends Problem> Predicate<A> goal();

    default Boolean constraints() {
        return true;
    }

    List<Integer> actions();

    <A extends Problem> A neighbor(Integer a);

    Integer weight(Integer a);


}
