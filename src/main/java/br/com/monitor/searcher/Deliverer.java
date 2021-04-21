package br.com.monitor.searcher;

public interface Deliverer<V> {

    V get(Long offset);

}
