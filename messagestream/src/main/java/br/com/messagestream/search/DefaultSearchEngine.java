package br.com.messagestream.search;

import br.com.messagecore.model.MessageFetcher;
import br.com.messagecore.model.SearchEngine;

import java.util.Optional;
import java.util.function.UnaryOperator;

public class DefaultSearchEngine {

//    public Optional<String> binarySearch(long start, long end, Deliverer<V> deliverer, Function<V, Long> br.com.messagestream.converter, Long value) {
//        long middle = (start + end) / 2;
//
//        var message = deliverer.get(middle);
//
//        if (message == null) {
//            return Optional.empty();
//        }
//
//        var retorno = br.com.messagestream.converter.apply(message);
//
//        if (retorno.equals(value)) {
//            return Optional.of((String) message);
//        } else if (retorno < value) {
//            start = middle + 1;
//        } else {
//            end = middle - 1;
//        }
//
//        return binarySearch(start, end, deliverer, br.com.messagestream.converter, value);
//    }

//    public static final Searcher binary = (long start, long end, Deliverer<?> deliverer, Long value) -> {
//        return Search.binary.apply(start, end, deliverer, null, value);
//    };

    @SuppressWarnings({"rawtypes", "unchecked"})
    public static final SearchEngine binary = (long start, long end, MessageFetcher<? extends Comparable<?>> messageFetcher, UnaryOperator<Comparable<?>> converter, Comparable<?> value, long previous) -> {
        long middle = (start + end) / 2;

        if (middle == previous)
            return Optional.empty();

        var message = messageFetcher.fetch(middle);
        if (message == null)
            return Optional.empty();

        Comparable recoveredValue = converter == null ? message : converter.apply(message);
        var result = recoveredValue.compareTo(value);

        if (result == 0)
            return Optional.of(message);
        else if (result < 0)
            start = middle + 1;
        else
            end = middle - 1;

        return DefaultSearchEngine.binary.apply(start, end, messageFetcher, converter, value, middle);
    };

    public static final SearchEngine sequencial = (long start, long end, MessageFetcher<? extends Comparable<?>> messageFetcher, UnaryOperator<Comparable<?>> converter, Comparable<?> value, long previous) -> {
        if (start == end)
            return Optional.empty();

        var message = messageFetcher.fetch(start);

        if (message == null)
            return Optional.empty();

        var recoveredValue = converter == null ? message : converter.apply(message);

        if (recoveredValue.equals(value))
            return Optional.of(message);

        return DefaultSearchEngine.sequencial.apply(start + 1, end, messageFetcher, converter, value, start);
    };
}
