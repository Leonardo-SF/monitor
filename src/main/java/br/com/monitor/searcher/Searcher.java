package br.com.monitor.searcher;

import java.util.Optional;
import java.util.function.UnaryOperator;

@FunctionalInterface
public interface Searcher {

    Optional<?> apply(long start, long end, Deliverer<? extends Comparable<?>> deliverer, UnaryOperator<Comparable<?>> converter, Comparable<?> value);

    default Optional<?> apply(long start, long end, Deliverer<? extends Comparable<?>> deliverer, Comparable<?> value) {
        return apply(start, end, deliverer, null, value);
    }

}
