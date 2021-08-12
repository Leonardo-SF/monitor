package br.com.messagecore.functional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;

public final class SafeRunner {

    private SafeRunner() {
    }

    @SuppressWarnings("unchecked")
    public static <V, E extends Exception> ExceptionHandler<V, E> run(SupplierThrowable<V, E> supplier) {
        try {
            return ExceptionHandler.success(supplier.apply());
        } catch (Exception e) {
            E err = (E) e;
            return ExceptionHandler.except(err);
        }
    }

    @SuppressWarnings("unchecked")
    public static <E extends Exception> LogStep run(VoidFunction<E> e) {
        try {
            e.apply();
            return ExceptionHandler.success(null);
        } catch (Exception o) {
            E err = (E) o;
            return ExceptionHandler.except(err);
        }
    }

    @SuppressWarnings("unchecked")
    public static <V, E extends Exception> V loggingErrors(SupplierThrowable<V, E> supplier) {
        return run(() -> supplier.apply())
                .orElseLog()
                .get();
    }

    public static <V, E extends Exception> void loggingErrors(VoidFunction<E> supplier) {
        run(() -> supplier.apply()).logOnError();
    }

    public interface LogStep {
        void logOnError();
    }

    //    public interface ErrorStep<V, E extends Exception> {
//        ReturnStep<V> orElseLog();
//
//        <E extends Exception> V orElseThrow() throws E;
//
//        <E2 extends Exception, E3 extends Exception> ExceptionHandler<V, E> orElseThrowsAs(Class<E2> e, Exception exc) throws E3;
//
//        <E extends Exception, E2 extends Exception, E3 extends Exception> ExceptionHandler<V, E> orElseThrowsAs(Class<E2> e, ConsumerThrowable<E, E3> c) throws E3;
//    }
//
    public interface ReturnStep<V, E extends Exception> {
        V get();

        Optional<V> getOptional();

        V get(Function<V, V> f);

        <T, E extends Exception> ExceptionHandler<T, E> flatMap(FunctionThrowable<? super V, T, E> mapper) throws E;
    }

    public static class ExceptionHandler<V, E extends Exception> implements LogStep, ReturnStep<V, E> { //ErrorStep<V, E>, ReturnStep<V> {

        private final Logger logger = LoggerFactory.getLogger(this.getClass());

        private final V value;
        private final E error;

        public ExceptionHandler(V value, E error) {
            this.value = value;
            this.error = error;
        }

        public static <V, E extends Exception> ExceptionHandler<V, E> except(E error) {
            return new ExceptionHandler<>(null, error);
        }

        public static <V, E extends Exception> ExceptionHandler<V, E> success(V value) {
            return new ExceptionHandler<>(value, null);
        }

        public <E extends Exception> V orElseThrow() throws E {
            return Optional.ofNullable(value).orElseThrow(() -> (E) error);
        }

        public ReturnStep<V, E> orElseLog() {
            Optional.ofNullable(error).ifPresent(e -> logger.error(e.getMessage()));
            return ExceptionHandler.success(value);
        }

        @Override
        public void logOnError() {
            Optional.ofNullable(error).ifPresent(e -> logger.error(e.getMessage()));
        }

        public <E2 extends Exception, E3 extends Exception> ExceptionHandler<V, E> orElseThrowsAs(Class<E2> e, Exception exc) throws E3 {
            if (error != null) {
                if (e.isInstance(error))
                    throw (E3) exc;

                return ExceptionHandler.except(error);
            }

            return ExceptionHandler.success(value);
        }

        public <E2 extends Exception, E3 extends Exception> ExceptionHandler<V, E> orElseThrowsAs(Class<E2> e, ConsumerThrowable<E, E3> c) throws E3 {
            if (error != null) {
                if (e.isInstance(error))
                    c.apply(error);

                return ExceptionHandler.except(error);
            }

            return ExceptionHandler.success(value);
        }

        @Override
        public Optional<V> getOptional() {
            return Optional.ofNullable(value);
        }

        @Override
        public V get(Function<V, V> f) {
            return f.apply(value);
        }

        @Override
        public V get() {
            return value;
        }

        public void andThen(Consumer<V> f) {
            f.accept(value);
        }

        @Override
        public <T, E extends Exception> ExceptionHandler<T, E> flatMap(FunctionThrowable<? super V, T, E> mapper) throws E {
            return (ExceptionHandler<T, E>) Optional.ofNullable(value)
                    .map((v) -> {
                        try {
                            return ExceptionHandler.success(mapper.apply(v));
                        } catch (Exception e) {
                            return ExceptionHandler.except(e);
                        }
                    }).orElse(ExceptionHandler.except(error));
        }

        public V fallback(Function<E, V> mapper) {
            return Optional.ofNullable(value).orElseGet(() -> mapper.apply(error));
        }
    }
}
