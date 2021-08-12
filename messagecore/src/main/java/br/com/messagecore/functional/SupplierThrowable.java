package br.com.messagecore.functional;

@FunctionalInterface
public interface SupplierThrowable<V, E extends Exception> {
    V apply() throws E;
}
