package br.com.monitor.functional;

@FunctionalInterface
public interface VoidFunction<E extends Exception> {

    void apply() throws E;

}
