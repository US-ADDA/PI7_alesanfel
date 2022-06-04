package main.java.tool;

import java.util.List;

public interface Problem {


    Boolean goal();

    default Boolean constraints() {
        return true;
    }

    List<Integer> actions();

    <A extends Problem> A neighbor(Integer a);

    Integer weight(Integer a);


}
