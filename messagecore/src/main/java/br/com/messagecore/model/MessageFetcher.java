package br.com.messagecore.model;

import java.io.Closeable;

public interface MessageFetcher<V> extends Closeable {

    V fetch(Long offset);

    void close();

}
