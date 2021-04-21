package br.com.monitor.functional;

@FunctionalInterface
public interface SupplierThrowable<V, E extends Exception> {
    V apply() throws E;
}
