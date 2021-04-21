package br.com.monitor.searcher;

import java.util.Optional;
import java.util.function.UnaryOperator;

public class Search {

//    public Optional<String> binarySearch(long start, long end, Deliverer<V> deliverer, Function<V, Long> converter, Long value) {
//        long middle = (start + end) / 2;
//
//        var message = deliverer.get(middle);
//
//        if (message == null) {
//            return Optional.empty();
//        }
//
//        var retorno = converter.apply(message);
//
//        if (retorno.equals(value)) {
//            return Optional.of((String) message);
//        } else if (retorno < value) {
//            start = middle + 1;
//        } else {
//            end = middle - 1;
//        }
//
//        return binarySearch(start, end, deliverer, converter, value);
//    }

//    public static final Searcher binary = (long start, long end, Deliverer<?> deliverer, Long value) -> {
//        return Search.binary.apply(start, end, deliverer, null, value);
//    };

    @SuppressWarnings({"rawtypes", "unchecked"})
    public static final Searcher binary = (long start, long end, Deliverer<? extends Comparable<?>> deliverer, UnaryOperator<Comparable<?>> converter, Comparable<?> value) -> {
        long middle = (start + end) / 2;

        var message = deliverer.get(middle);
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

        return Search.binary.apply(start, end, deliverer, converter, value);
    };

    public static final Searcher sequencial = (long start, long end, Deliverer<? extends Comparable<?>> deliverer, UnaryOperator<Comparable<?>> converter, Comparable<?> value) -> {
        if (start == end)
            return Optional.empty();

        var message = deliverer.get(start);

        if (message == null)
            return Optional.empty();

        var recoveredValue = converter == null ? message : converter.apply(message);

        if (recoveredValue.equals(value))
            return Optional.of(message);

        return Search.sequencial.apply(start + 1, end, deliverer, converter, value);
    };
}
