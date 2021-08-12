package br.com.messagecore.functional;

@FunctionalInterface
public interface VoidFunction<E extends Exception> {

    void apply() throws E;

}
