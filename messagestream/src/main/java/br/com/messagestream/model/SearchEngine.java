package br.com.messagestream.model;

import java.util.Optional;
import java.util.function.UnaryOperator;

@FunctionalInterface
public interface SearchEngine {

    Optional<?> apply(long start, long end, MessageFetcher<? extends Comparable<?>> fetch, UnaryOperator<Comparable<?>> converter, Comparable<?> value, long previous);

}
