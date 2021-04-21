package br.com.monitor.functional;

@FunctionalInterface
public interface FunctionThrowable<V, R, E extends Exception> {

    R apply(V v) throws E;

}
