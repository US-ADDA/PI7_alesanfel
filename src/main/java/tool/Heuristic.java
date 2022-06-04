package main.java.tool;

import java.util.function.Predicate;

public interface Heuristic<P extends Problem> {

    Double heuristic(P source, Predicate<P> goal, P target);

    Double limit(P v, Integer a);
}
