package exceptions;

public class MetricException extends Exception {

    private MetricException(String msg) {
        super(msg);
    }

    private MetricException(String msg, Exception e) {
        super(msg, e);
    }

    public static class MetricReadException extends MetricException {
        public MetricReadException(String msg) {
            super(msg);
        }

        public MetricReadException(String msg, Exception e) {
            super(msg, e);
        }
    }

    public static class MetricWriteException extends MetricException {
        public MetricWriteException(String msg) {
            super(msg);
        }

        public MetricWriteException(String msg, Exception e) {
            super(msg, e);
        }
    }

}
